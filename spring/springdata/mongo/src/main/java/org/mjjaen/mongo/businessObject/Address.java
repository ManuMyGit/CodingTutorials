package org.mjjaen.mongo.businessObject;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import java.io.Serializable;

@Data
@AllArgsConstructor
@ToString
@Builder
public class Address implements Serializable {
    private static final long serialVersionUID = -5409793991784058124L;
    private String city;
    private String state;
    private String zipCode;
    private String country;
}