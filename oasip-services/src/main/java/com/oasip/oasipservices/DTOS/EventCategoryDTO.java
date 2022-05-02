package com.oasip.oasipservices.DTOS;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Time;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventCategoryDTO {
    private Integer id;
    private String eventCategoryName;
    private String eventCategoryDescription;
    private Time eventDuration;

}