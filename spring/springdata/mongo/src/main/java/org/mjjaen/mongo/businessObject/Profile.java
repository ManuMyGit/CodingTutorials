package org.mjjaen.mongo.businessObject;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@AllArgsConstructor
@ToString
@Builder
public class Profile {
    @Id
    private ObjectId id;
    private String email;
    private String phoneNumber;
}
