package org.mjjaen.asynccoding.executableExamples;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RunnableExample implements Executable, Runnable {
    @Override
    public void execute(Object obect) {
        Thread t1 = new Thread(new RunnableExample());
        log.info("Thread state: " + t1.getState());
        t1.start();
        log.info("Thread state: " + t1.getState());
        //Delay to let the thread finish so the status gets updated to terminated
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("Thread state: " + t1.getState());
    }

    @Override
    public void run() {
        log.info("This simulates a execution of a real task");
    }
}
