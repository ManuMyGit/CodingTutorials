package org.mjjaen.aop.businessObject;

import lombok.*;

@Data
@AllArgsConstructor
@Builder
@ToString
public class Book {
    private String id;
    private String name;
    private String isbn;
}
