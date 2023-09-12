package org.mjjaen.restapi.dto;

import lombok.*;

import javax.validation.constraints.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskDto {
    private String id;
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
    private LocalDateTime dateCreated;
    private LocalDateTime lastUpdated;
}