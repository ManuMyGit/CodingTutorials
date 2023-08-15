package org.mjjaen.asynccoding.executableExamples;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

@Slf4j
public class FutureExample implements Executable {
    final ExecutorService threadPool = Executors.newSingleThreadExecutor();

    @Override
    public void execute(Object obect) {
        try {
            Runnable task = () -> {
                log.info("This task waits for 1 second and returns a value.");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            };
            Future<?> future = threadPool.submit(task);
            log.info("The future method get() will block the execution of the program until the task is done.");
            log.info("The future was just created, so it is not cancelled. Is CANCELLED? " + future.isCancelled());
            future.get();
            log.info("This line will be printed always after the lines before the get method due to the get() blocking behavior.");
            log.info("Since the get is a blocking call, the future will be done by this time. Is DONE? " + future.isDone());
            log.info("---------------------------------------------------------------------------------------------------------");
            log.info("A new future is gonna be created.");
            future = threadPool.submit(task);
            try {
                log.info("The blocking get method is called, but with a timeout of 500 ms. Since the task takes 1000 ms to run, a exception will be thrown.");
                future.get(500, TimeUnit.MILLISECONDS);
            } catch (TimeoutException e) {
                log.info("The exception TimeoutException has been thrown because the task took more than the timeout stablished.");
            }
            log.info("A new future is gonna be created and cancelled automatically");
            future = threadPool.submit(task);
            future.cancel(true);
            log.info("The future was just cancelled. Is CANCELLED? " + future.isCancelled() + ". Is DONE? " + future.isDone());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } finally {
            threadPool.shutdown();
        }
    }
}
