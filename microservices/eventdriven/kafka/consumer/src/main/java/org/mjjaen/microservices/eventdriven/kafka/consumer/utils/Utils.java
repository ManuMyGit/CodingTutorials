package org.mjjaen.microservices.eventdriven.kafka.consumer.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class Utils {
    public static Map<String, Object> convertPropertiesToMap(Properties properties) {
        Map step1 = properties;
        Map<String, Object> step2 = (Map<String, Object>) step1;
        return new HashMap<>(step2);
    }
}
