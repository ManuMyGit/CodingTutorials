package org.mjjaen.asynccoding.businessObject;

import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;

@Slf4j
public class ThreadSafeDateFormatter {
    public static ThreadLocal<SimpleDateFormat> dateFormatThreadLocal = ThreadLocal.withInitial(() -> {
        log.info("Creation method called");
        return new SimpleDateFormat("yyyy-MM-dd");
    });
}
