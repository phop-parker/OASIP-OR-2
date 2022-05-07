package com.oasip.oasipservices.DTOS;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventBookingCategoryDTO {
    private Integer id;
    private String eventCategoryName;
    private Integer eventDuration;
}
