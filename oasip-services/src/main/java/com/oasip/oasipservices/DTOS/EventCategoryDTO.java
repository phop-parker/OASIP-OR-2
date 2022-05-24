package com.oasip.oasipservices.DTOS;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class EventCategoryDTO {
    private Integer categoryId;
    @NotBlank(message="eventCategoryName can't be blank")
    @Size(max = 100,message = "event category name must be between 0-100 characters")
    private String eventCategoryName;
    @Size(max = 500,message = "event category description must be between 0-500 characters")
    private String eventCategoryDescription;
    @NotNull(message="eventDuration can't be blank")
    @Min(value=1, message="event Duration must be equal or greater than 1 min.")
    @Max(value=480, message="event Duration must be equal or less than 480 min.")
    private Integer eventDuration;
}
