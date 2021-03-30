package org.mjjaen.syncasyncserver.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mjjaen.syncasyncserver.manager.SampleManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@RestController
public class AsyncController {
    private static final Logger logger = LogManager.getLogger(AsyncController.class);

    @Autowired
    private Executor executor;

    @Autowired
    private SampleManager sampleManager;

    @GetMapping("/async")
    public CompletableFuture<ResponseEntity<String>> getController() {
        long init = System.currentTimeMillis();
        logger.info("Beginning: " + init);
        CompletableFuture<ResponseEntity<String>> response = CompletableFuture.supplyAsync(() -> {
            sampleManager.doNothingAndWait();
            return ResponseEntity.ok("");
        }, executor);
        long end = System.currentTimeMillis();
        logger.info("Time needed: " + (end - init));
        return response;
    }
}
