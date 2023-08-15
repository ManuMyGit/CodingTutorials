package org.mjjaen.asynccoding.executableExamples;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.*;

@Slf4j
public class SemaphoreExample implements Executable {
    //Lock managemnet by using the Semaphore
    private final Semaphore semaphore = new Semaphore(1, true);
    public static Stack<Integer> stack = new Stack<>();

    @Override
    public void execute(Object obect) {
        List<Callable<Void>> tasks = new ArrayList<>(10);
        for (int i = 0; i < 10; i++) {
            final Integer count = i;
            Callable<Void> task = () -> {
                semaphore.acquire();
                Thread.sleep((long) Math.floor(Math.random() * (500 - 1 + 1) + 1));
                log.info("This is the count inserted: " + count);
                stack.push(count);
                semaphore.release();
                return null;
            };
            tasks.add(task);
        }
        ExecutorService threadPool = Executors.newFixedThreadPool(10);
        try {
            threadPool.invokeAll(tasks);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            threadPool.shutdown();
        }
        try {
            while (threadPool.awaitTermination(2, TimeUnit.SECONDS)) {
                if (stack.pop() == 9 && stack.pop() == 8 && stack.pop() == 7 && stack.pop() == 6 && stack.pop() == 5 && stack.pop() == 4 && stack.pop() == 3 && stack.pop() == 2 && stack.pop() == 1 && stack.pop() == 0) {
                    log.info("The operation has been successfully controlled by the semaphore");
                } else
                    log.error("Something is wrong!");
                break;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}