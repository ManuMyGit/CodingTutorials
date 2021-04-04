package org.mjjaen.asynccoding.executableExamples;

import lombok.extern.slf4j.Slf4j;
import org.mjjaen.asynccoding.businessObject.MonitorThreadFactory;
import org.mjjaen.asynccoding.businessObject.ScheduledPoolMonitor;
import org.mjjaen.asynccoding.businessObject.RejectedExecutionHandlerImpl;

import java.util.concurrent.*;

@Slf4j
public class ExecutorExample implements Executable {
    @Override
    public void execute(Object obect) {
        try {
            singleThreadExample();
            fixedThreadExample();
            cachedThreadExample();
            customAndScheduledThreadExample();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void singleThreadExample() throws InterruptedException {
        //To create 2 tasks and to see that they are not executed in parallel. What about the queue? What about the task rejection?
        long init = System.currentTimeMillis();
        ExecutorService singleThreadPool = Executors.newSingleThreadExecutor();
        Runnable task = () -> {
            log.info("Task is being executed, waiting period.");
            ExecutorExample.sleep(1000);
            log.info("This line will appear always after the task being executed, since there is no chance of parallelism with a single thread.");
        };
        try {
            singleThreadPool.submit(task);
            singleThreadPool.submit(task);
            singleThreadPool.submit(task);
            singleThreadPool.shutdown();
            while (!singleThreadPool.awaitTermination(5, TimeUnit.SECONDS)) {
            }
            long end = System.currentTimeMillis();
            log.info("The total time is " + (end - init) + "ms. This matches with the 3 tasks of 1 second being executed idividually");
        } finally {
            singleThreadPool.shutdown();
        }
    }

    private void fixedThreadExample() throws InterruptedException {
        long init = System.currentTimeMillis();
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(2);
        Runnable task = () -> {
            log.info("Task is being executed, waiting period.");
            ExecutorExample.sleep(1000);
        };
        try {
            fixedThreadPool.submit(task);
            fixedThreadPool.submit(task);
            fixedThreadPool.submit(task);
            fixedThreadPool.submit(task);
            fixedThreadPool.submit(task);
            fixedThreadPool.submit(task);
            fixedThreadPool.shutdown();
            while (!fixedThreadPool.awaitTermination(5, TimeUnit.SECONDS)) {
            }
            long end = System.currentTimeMillis();
            log.info("The total time is " + (end - init) + "ms. This matches with the 6 tasks of 1 second being executed in set of 2 parallely");
        } finally {
            fixedThreadPool.shutdown();
        }
    }

    private void cachedThreadExample() throws InterruptedException {
        long init = System.currentTimeMillis();
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
        Runnable task = () -> {
            log.info("Task is being executed, waiting period.");
            ExecutorExample.sleep(1000);
        };
        try {
            for (int i = 0; i < 100; i++)
                cachedThreadPool.submit(task);
            cachedThreadPool.shutdown();
            while (!cachedThreadPool.awaitTermination(5, TimeUnit.SECONDS)) {
            }
            long end = System.currentTimeMillis();
            log.info("The total time is " + (end - init) + "ms. It doesn't matter how many tasks, the pool will always have or create threads available.");
        } finally {
            cachedThreadPool.shutdown();
        }
    }

    private void customAndScheduledThreadExample() throws InterruptedException {
        ThreadPoolExecutor customThreadPool = new ThreadPoolExecutor(2, 4, 10, TimeUnit.SECONDS, new ArrayBlockingQueue<>(2), Executors.defaultThreadFactory(), new RejectedExecutionHandlerImpl());
        //To monitor the previous queue
        ScheduledExecutorService monitorThread = Executors.newScheduledThreadPool(1, new MonitorThreadFactory());
        monitorThread.scheduleAtFixedRate(new ScheduledPoolMonitor(customThreadPool) , 0, 500, TimeUnit.MILLISECONDS);
        try {
            log.info("Custom pool with min = 2 threads, max = 4 threads, idle time = 60 seconds and a queue of size 2");
            log.info("If 7 tasks are submitted at the same time, 1 of them will be rejected, because there will be 4 tasks at the pool + 2 waiting on the queue");
            Runnable task = () -> {
                ExecutorExample.sleep(5000);
                log.info("Task finished.");
            };
            log.info("Submitting task 1 ...");
            customThreadPool.submit(task);
            log.info("Task submitted.");
            log.info("Submitting task 2 ...");
            customThreadPool.submit(task);
            log.info("Task submitted.");
            log.info("Submitting task 3 ...");
            customThreadPool.submit(task);
            log.info("Task submitted.");
            log.info("Submitting task 4 ...");
            customThreadPool.submit(task);
            log.info("Task submitted.");
            log.info("Submitting task 5 ...");
            customThreadPool.submit(task);
            log.info("Task submitted.");
            log.info("Submitting task 6 ...");
            customThreadPool.submit(task);
            log.info("Task submitted.");
            log.info("Submitting task 7 ...");
            customThreadPool.submit(task);
        } finally {
            customThreadPool.shutdown();
            while(!customThreadPool.awaitTermination(10, TimeUnit.SECONDS));
            monitorThread.shutdown();
        }
    }

    private static void sleep(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}