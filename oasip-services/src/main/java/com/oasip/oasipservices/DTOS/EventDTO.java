package com.oasip.oasipservices.DTOS;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Time;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.FormatStyle;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventDTO {
    private Integer id;
    private String bookingName;
    private String bookingEmail;
    @JsonFormat(pattern="dd/MM/yyyy, HH:mm:ss")
    private LocalDateTime eventStartTime;
    private Integer eventDuration;
    private String eventNotes;
    private String eventCategoryName;
}
