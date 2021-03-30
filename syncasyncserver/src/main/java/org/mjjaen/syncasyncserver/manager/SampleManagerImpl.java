package org.mjjaen.syncasyncserver.manager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class SampleManagerImpl implements SampleManager{
    private static final Logger logger = LogManager.getLogger(SampleManagerImpl.class);
    public void doNothingAndWait() {
        long init = System.currentTimeMillis();
        logger.info("Beginning: " + init);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        logger.info("Time needed: " + (end - init));
    }
}
