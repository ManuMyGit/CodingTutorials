package org.mjjaen.microservices.eventdrivenrabbitmq.rconfiguration.businessObject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageBody implements Serializable {
    private String message;
}
