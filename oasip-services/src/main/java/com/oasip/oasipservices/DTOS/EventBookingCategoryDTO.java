package com.oasip.oasipservices.DTOS;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class EventCategoriesDTO {
    private Integer categoryId;
    private String eventCategoryName;
    private String eventCategoryDescription;
    @Min(value=1, message="event Duration must be equal or greater than 1 min.")
    @Max(value=480, message="event Duration must be equal or less than 480 min.")
    private Integer eventDuration;
}