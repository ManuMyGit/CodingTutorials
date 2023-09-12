package org.mjjaen.restapi.model;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Task {
    @Id
    private ObjectId id;
    @NotBlank(message = "Description is mandatory")
    private String description;
    @NotBlank(message = "Definition of Done is mandatory")
    private String definitionOfDone;
    @NotBlank(message = "Priority is mandatory")
    @Pattern(regexp = "LOW|MEDIUM|HIGH", message = "Priority value not valid")
    private String priority;
    @PositiveOrZero(message = "dataOne must be greater or equal than 0")
    @NotNull(message="dataOne is required")
    private Integer dataOne;
    @PositiveOrZero(message = "dataTwo must be greater or equal than 0")
    @NotNull(message="dataTwo is required")
    private Integer dataTwo;
    @PositiveOrZero(message = "dataThree must be greater or equal than 0")
    @NotNull(message="dataThree is required")
    private Integer dataThree;
    @PositiveOrZero(message = "dataFour must be greater or equal than 0")
    @NotNull(message="dataFour is required")
    private Integer dataFour;
    @CreatedDate
    private LocalDateTime dateCreated;
    @LastModifiedDate
    private LocalDateTime lastUpdated;
    @Version
    private Integer version;
}
