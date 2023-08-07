package org.mjjaen.asynccoding.businessObject;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ThreadPoolExecutor;

@Slf4j
public class ScheduledPoolMonitor implements Runnable {
    private final ThreadPoolExecutor executor;

    public ScheduledPoolMonitor(ThreadPoolExecutor executor) {
        this.executor = executor;
    }

    @Override
    public void run() {
        log.info(
                String.format("[%d/%d] Active: %d, Completed: %d, Task: %d, isShutdown: %s, isTerminated: %s",
                        this.executor.getPoolSize(),
                        this.executor.getCorePoolSize(),
                        this.executor.getActiveCount(),
                        this.executor.getCompletedTaskCount(),
                        this.executor.getTaskCount(),
                        this.executor.isShutdown(),
                        this.executor.isTerminated()));
    }
}
