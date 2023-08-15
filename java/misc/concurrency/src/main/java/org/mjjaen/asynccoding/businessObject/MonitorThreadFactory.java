package org.mjjaen.asynccoding.businessObject;

import java.util.concurrent.ThreadFactory;

public class MonitorThreadFactory implements ThreadFactory {
    public Thread newThread(Runnable r) {
        return new Thread(r, "Monitor thread");
    }
}