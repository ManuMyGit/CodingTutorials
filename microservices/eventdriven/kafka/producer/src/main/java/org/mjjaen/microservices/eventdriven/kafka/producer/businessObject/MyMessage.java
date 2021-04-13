package org.mjjaen.microservices.eventdriven.kafka.producer.businessObject;

import lombok.Data;

import java.io.Serializable;

@Data
public class MyMessage  implements Serializable {
    private String message;
}
