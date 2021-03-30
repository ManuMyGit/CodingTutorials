package org.mjjaen.syncasyncserver.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mjjaen.syncasyncserver.manager.SampleManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SyncController {
    private static final Logger logger = LogManager.getLogger(SyncController.class);

    @Autowired
    private SampleManager sampleManager;

    /*
     * Undertow I/O threads: by default, number of logical threads. On my computer, 12 threads.
     * Working threads: by default, 8 * CPU Cores * Threads per core. On my computer, 96.
     * In means that in this example, we could handle 96 requests in ~2000ms (due to the synchronous and blocking management) successfully, but not 97.
     * If a JMeter is configured to run 97 parallel calls with a timeout of 2200ms, 1 of them will fail.
     */
    @GetMapping("/sync")
    public ResponseEntity<String> getController() {
        long init = System.currentTimeMillis();
        logger.info("Beginning: " + init);
        sampleManager.doNothingAndWait();
        long end = System.currentTimeMillis();
        logger.info("Time needed: " + (end - init));
        return ResponseEntity.ok("");
    }

}
