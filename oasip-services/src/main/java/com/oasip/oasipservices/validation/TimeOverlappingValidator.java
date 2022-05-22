package com.oasip.oasipservices.validation;

import com.oasip.oasipservices.DTOS.CreateNewEventDTO;
import com.oasip.oasipservices.DTOS.EventCategoryDTO;
import com.oasip.oasipservices.entities.Event;
import com.oasip.oasipservices.repositories.EventRepository;
import com.oasip.oasipservices.services.EventCategoryService;
import com.oasip.oasipservices.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;
import java.util.List;

public class TimeOverlappingValidator implements ConstraintValidator<TimeOverlapping, CreateNewEventDTO> {

        @Autowired
        private EventRepository repository;

        @Autowired
        private EventCategoryService categoryService;

        @Autowired
        private EventService service;

        @Override
        public void initialize(TimeOverlapping constraintAnnotation) {
        }

        @Override
        public boolean isValid(CreateNewEventDTO event, ConstraintValidatorContext cxt) {
//                if(event.getCategoryId() == null ){
//                        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Event category can not be null");
//                }else if(event.getEventStartTime() == null ){
//                        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Event startTime can not be null");
//                }

                try {   EventCategoryDTO eventCategory = categoryService.getAllCategoryById(event.getCategoryId());
                        LocalDateTime EndDateTime = service.findEndDate(event.getEventStartTime(), eventCategory.getEventDuration());
                        List<Event> eventOverlapping = repository.findOverlappingEvents(event.getCategoryId(), event.getEventStartTime(), EndDateTime);
                        if (eventOverlapping.size() >= 1) {
                                return false;
                        } else{
                                return true;
                        }
                } catch(Exception ex) {
                        return false;
                }
        }
}
