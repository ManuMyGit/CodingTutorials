package org.mjjaen.asynccoding.executableExamples;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ThreadExample extends Thread implements Executable {
    @Override
    public void execute(Object obect) {
        ThreadExample threadExample = new ThreadExample();
        log.info("Thread state: " + threadExample.getState());
        threadExample.start();
        log.info("Thread state: " + threadExample.getState());
        //Delay to let the thread finish so the status gets updated to terminated
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("Thread state: " + threadExample.getState());
    }

    @Override
    public void run() {
        log.info("This is a sentence printed by a thread");
    }
}
