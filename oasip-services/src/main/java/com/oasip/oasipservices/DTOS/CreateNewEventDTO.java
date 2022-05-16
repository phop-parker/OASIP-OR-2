package com.oasip.oasipservices.DTOS;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

import com.oasip.oasipservices.entities.EventCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class CreateNewEventDTO {
    @NotBlank(message="name can't be blank")
    @Size(max = 100,message = "booking name must be between 0-100 characters")
    private String bookingName;

    @NotBlank(message="bookingEmail can't be blank")
    @Size(max = 50,message = "booking email must be between 0-50 characters")
    @Email(message="invalid email format.")
    private String bookingEmail;

    @NotNull(message="eventStartTime can't be null")
    @Future(message="required future date time")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventStartTime;
    private Integer eventDuration;

    @NotNull(message="event category can't be null")
    private Integer categoryId;

    @Size(max = 500,message = "event note must be between 0-500 characters")
    private String eventNotes;

}
