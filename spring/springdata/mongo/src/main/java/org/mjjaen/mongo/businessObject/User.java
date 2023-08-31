package org.mjjaen.mongo.businessObject;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Document
@Data
@AllArgsConstructor
@ToString
@Builder
public class User implements Serializable {
    private static final long serialVersionUID = -5409797972787008124L;
    @Id
    private ObjectId id;
    private String name;
    private Map<String, String> userSettings = new HashMap<>();
    //Address is stored as en embedded entity
    private Address address;
    //Manual reference
    private ObjectId profile;
    //DB Reference
    @DBRef(lazy = true)
    private List<Book> books;

    @CreatedDate
    private LocalDateTime dateCreated;
    @LastModifiedDate
    private LocalDateTime lastUpdated;
    @Version
    private Integer version;
}
