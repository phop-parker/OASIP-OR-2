package com.oasip.oasipservices.validators;

import com.oasip.oasipservices.DTOS.CreateNewEventDTO;
import com.oasip.oasipservices.entities.Event;
import com.oasip.oasipservices.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class ConstraintNameDateValidator implements ConstraintValidator<ConstraintNameDate, CreateNewEventDTO> {

    @Autowired
    private EventRepository repository;

    @Override
    public void initialize(ConstraintNameDate constraintAnnotation) {}

    @Override
    public boolean isValid(CreateNewEventDTO event, ConstraintValidatorContext cxt) {
        try {
            List<Event> eventConstraint = repository.findConstraintEvent(event.getBookingName(), event.getEventStartTime());
            if (eventConstraint.size() >= 1) {return false;}
            else{return true;}
        }catch(Exception ex) {
            return false;
        }}}
