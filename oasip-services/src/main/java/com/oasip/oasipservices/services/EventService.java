package com.oasip.oasipservices.services;

import com.oasip.oasipservices.DTOS.CreateNewEventDTO;
import com.oasip.oasipservices.DTOS.EditedEventDTO;
import com.oasip.oasipservices.DTOS.EventDTO;
import com.oasip.oasipservices.entities.Event;
import com.oasip.oasipservices.entities.EventCategory;
import com.oasip.oasipservices.repositories.EventCategoryRepository;
import com.oasip.oasipservices.repositories.EventRepository;
import com.oasip.oasipservices.utils.ListMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.*;
import java.util.List;

@Service

public class EventService {

    @Autowired
    private ListMapper listMapper;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private EventRepository repository;

    @Autowired
    private EventCategoryRepository eventCategoryRepository;

    public Event save(CreateNewEventDTO event) {
        Event newEvent = modelMapper.map(event, Event.class);
        EventCategory eventCategory = eventCategoryRepository.findById(event.getCategoryId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Event id " + event.getCategoryId() + "Does Not Exist !!!"));
        newEvent.setEventDuration(eventCategory.getEventDuration());
        newEvent.setEventId(null);
        newEvent.setCategory(eventCategory);
        checkConstraints(newEvent.getBookingName(),newEvent.getEventStartTime());
        checkOverlapping(newEvent.getEventStartTime(),newEvent.getEventDuration(), newEvent.getCategory().getCategoryId());
        return repository.saveAndFlush(newEvent);
    }

    public List<EventDTO> getAllEvent() {
        List<Event> eventList = repository.findAllByOrderByEventStartTimeDesc();
        return listMapper.mapList(eventList, EventDTO.class, modelMapper);
    }

    public EventDTO getEventById(Integer id) {
        Event event = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Event id " + id +
                        "Does Not Exist !!!"));
        return modelMapper.map(event, EventDTO.class);
    }

    private void checkOverlapping (LocalDateTime updateDateTime,Integer newEventDuration,Integer categoryId){
        LocalDateTime newEventStartTime = updateDateTime;
        LocalDateTime newEventEndTime = findEndDate(newEventStartTime, newEventDuration);
        List<Event>  overlapAmount  = repository.findOverlappingEvents(categoryId,newEventStartTime,newEventEndTime);
        if(overlapAmount.size() >= 1){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Time is overlapping");
        }
    }
    private LocalDateTime findEndDate(LocalDateTime eventStartTime, Integer duration) {
        LocalDateTime getEventEndTime = eventStartTime.plusMinutes(duration);
        return getEventEndTime;
    }

    public EditedEventDTO updateEvent(Event updateEvent, Integer id) {
        EventDTO oldEvent = getEventById(id);
        if(updateEvent.getEventNotes() == null ){
            updateEvent.setEventNotes(oldEvent.getEventNotes());
        }
        if(updateEvent.getEventStartTime() == null ){
            updateEvent.setEventStartTime(oldEvent.getEventStartTime());
        }
        Event editEvent = repository.findById(id).map(event -> {
            event.setEventNotes(updateEvent.getEventNotes());
            event.setEventStartTime(updateEvent.getEventStartTime());
            return event;
        }).orElseGet(() -> {
            updateEvent.setEventId(id);
            return updateEvent;
        });
        repository.saveAndFlush(editEvent);
        return modelMapper.map(editEvent, EditedEventDTO.class);
    }
    public void checkConstraints(String bookingName, LocalDateTime eventStartTime){
        List<Event> constraintEvent = repository.findConstraintEvent(bookingName,eventStartTime);
        if(constraintEvent.size() >= 1){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"name and datetime is already booked");
        }
    }
    public void delete(Integer id) {
        repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                id + " does not exist !!!"));
        repository.deleteById(id);
    }

}