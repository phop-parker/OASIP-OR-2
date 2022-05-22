package com.oasip.oasipservices.DTOS;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventDTO {
    private Integer id;
    private String bookingName;
    private String bookingEmail;
    @JsonFormat(pattern="yyyy-MM-dd@HH:mm:ss")
    private LocalDateTime eventStartTime;
    private Integer eventDuration;
    private String eventNotes;
    private String eventCategoryName;
}
