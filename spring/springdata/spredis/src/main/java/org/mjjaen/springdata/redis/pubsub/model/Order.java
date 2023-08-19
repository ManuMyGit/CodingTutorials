package org.mjjaen.springdata.redis.pubsub.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order implements Serializable {
    private static final long serialVersionUID = -7117121776121728682L;

    private int orderId;
    private int userId;
    private String productName;
    private int price;
    private int quantity;
}