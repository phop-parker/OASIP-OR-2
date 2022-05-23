package com.oasip.oasipservices.DTOS;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EditedEventDTO {
    private Integer id;
    @Future(message="required future date time")
    private LocalDateTime eventStartTime;
    @Size(max = 500,message = "event note must be between 0-500 characters")
    private String eventNotes;
}
