package com.oasip.oasipservices.services;

import com.oasip.oasipservices.DTOS.CreateNewEventDTO;
import com.oasip.oasipservices.DTOS.EditedEventDTO;
import com.oasip.oasipservices.DTOS.EventDTO;
import com.oasip.oasipservices.DTOS.EventListDTO;
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

    public List<EventListDTO> getAllEvent() {
        List<Event> eventList = repository.findAllByOrderByEventStartTimeDesc();
        return listMapper.mapList(eventList, EventListDTO.class, modelMapper);
    }

    public EventDTO getEventById(Integer id) {
        Event event = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Event id " + id +
                        "Does Not Exist !!!"));
        return modelMapper.map(event, EventDTO.class);
    }


    public Integer delete(Integer id) {
        repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                id + " does not exist !!!"));
        repository.deleteById(id);
        return id;
    }


    public EventDTO save(CreateNewEventDTO event) {
        Event newEvent = modelMapper.map(event, Event.class);
        EventCategory eventCategory = eventCategoryRepository.findById(event.getCategoryId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Event id " + event.getCategoryId() + " Does Not Exist !!!"));
        newEvent.setEventDuration(eventCategory.getEventDuration());
        newEvent.setEventId(null);
        newEvent.setCategory(eventCategory);
        newEvent.setBookingName(event.getBookingName().trim());
        newEvent.setBookingEmail(event.getBookingEmail().trim());
        if(event.getEventNotes()!=null){
            newEvent.setEventNotes(event.getEventNotes().trim());
        }
        checkConstraints(newEvent.getBookingName(),newEvent.getEventStartTime());
        repository.saveAndFlush(newEvent);
        return modelMapper.map(newEvent, EventDTO.class);
    }

    public void checkConstraints(String bookingName, LocalDateTime eventStartTime){
        List<Event> constraintEvent = repository.findConstraintEvent(bookingName,eventStartTime);
        if(constraintEvent.size() >= 1){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"name and datetime is already booked");
        }
    }

    public LocalDateTime findEndDate(LocalDateTime eventStartTime, Integer duration) {
        LocalDateTime EventEndTime = eventStartTime.plusMinutes(duration);
        return EventEndTime;
    }

    public void overlappingEdit(Integer eventId,LocalDateTime newEventStartTime, LocalDateTime newEventEndTime){
        EventCategory category = eventCategoryRepository.findEventCategoryByEventId(eventId);
        List<Event> overlappingEvent = repository.findOverlappingEventForEdit(eventId,category.getCategoryId(),newEventStartTime,newEventEndTime);

        if(overlappingEvent.size() >= 1){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Editted time is overlapping");
        }
    }

    public EditedEventDTO updateEvent(Event updateEvent, Integer id) {
        EventDTO oldEvent = getEventById(id);
        if(updateEvent.getEventNotes() == null ){
            updateEvent.setEventNotes(oldEvent.getEventNotes());
        }
        if(updateEvent.getEventStartTime() == null ){
            updateEvent.setEventStartTime(oldEvent.getEventStartTime());
        }
        overlappingEdit(id,updateEvent.getEventStartTime(),findEndDate(updateEvent.getEventStartTime(),oldEvent.getEventDuration()));
        Event editEvent = repository.findById(id).map(event -> {
            event.setEventNotes(updateEvent.getEventNotes().trim());
            event.setEventStartTime(updateEvent.getEventStartTime());
            return event;
        }).orElseGet(() -> {
            updateEvent.setEventId(id);
            return updateEvent;
        });
        repository.saveAndFlush(editEvent);
        return modelMapper.map(editEvent, EditedEventDTO.class);
    }

}