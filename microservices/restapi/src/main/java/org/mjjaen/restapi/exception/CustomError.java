package org.mjjaen.restapi.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class CustomError {
    private String message;
    private String details;
    private LocalDateTime localDateTime;
}
