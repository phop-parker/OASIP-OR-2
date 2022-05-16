package com.oasip.oasipservices.DTOS;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Future;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EditedEventDTO {
    private Integer id;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Future(message="required future date time")
    private LocalDateTime eventStartTime;
    @Size(max = 500,message = "event note must be between 0-500 characters")
    private String eventNotes;
}
