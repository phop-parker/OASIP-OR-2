package com.oasip.oasipservices.services;

import com.oasip.oasipservices.DTOS.CreateNewEventDTO;
import com.oasip.oasipservices.DTOS.EditedEventDTO;
import com.oasip.oasipservices.DTOS.EventCategoriesDTO;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;

@Service

public class EventService {

    @Autowired
    private ListMapper listMapper;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private EventRepository repository;

    @Autowired
    private EventCategoryService eventCategoryService;

    public Event save(CreateNewEventDTO event) {
        EventCategoriesDTO eventCategory = eventCategoryService.getAllCategoryById(event.getCategoryId());
        event.setEventDuration(eventCategory.getEventDuration());
        Event newEvent = modelMapper.map(event, Event.class);
        if(checkDateTimeFuture(newEvent.getEventStartTime(),newEvent.getCategoryId().getEventCategoryName(),newEvent.getEventDuration())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Time is overlapping");}
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

    private Boolean checkDateTimeFuture(LocalDateTime updateDateTime, String newEventCategoryName,
                                     Integer newEventDuration) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        LocalDateTime newEventStartTime = updateDateTime;
        LocalDateTime newEventEndTime = findEndDate(newEventStartTime, newEventDuration);
        List<EventDTO> eventList = getAllEvent();
        for (int i = 0; i < eventList.size(); i++) {
            LocalDateTime eventStartTime = eventList.get(i).getEventStartTime();
            LocalDateTime eventEndTime = findEndDate(eventList.get(i).getEventStartTime(),
                    eventList.get(i).getEventDuration());
            String eventCategory = eventList.get(i).getEventCategoryName();
            if (eventCategory.equalsIgnoreCase(newEventCategoryName)) {
                if (newEventStartTime.isBefore(eventStartTime) && newEventEndTime.isAfter(eventStartTime) ||
                        newEventStartTime.isBefore(eventEndTime) && newEventEndTime.isAfter(eventEndTime) ||
                        newEventStartTime.isBefore(eventStartTime) && newEventEndTime.isAfter(eventEndTime) ||
                        newEventStartTime.isAfter(eventStartTime) && newEventEndTime.isBefore(eventEndTime) ||
                        newEventStartTime.isEqual(eventStartTime)) {
                    return true;
                } else{
                    return false;
                }
            }
        }
        return false;
    }

    private LocalDateTime findEndDate(LocalDateTime eventStartTime, Integer duration) {
        LocalDateTime getEventEndTime = eventStartTime.plusMinutes(duration);
        return getEventEndTime;
    }

    public EditedEventDTO updateEvent(Event updateEvent, Integer id) {
        Event oldEvent = repository.getById(id);
        if(updateEvent.getEventNotes() == null && updateEvent.getEventStartTime() == null ){
            updateEvent.setBookingName(oldEvent.getBookingName());
            updateEvent.setBookingEmail(oldEvent.getBookingEmail());
            updateEvent.setEventStartTime(oldEvent.getEventStartTime());
            updateEvent.setEventDuration(oldEvent.getEventDuration());
            updateEvent.setEventNotes(oldEvent.getEventNotes());
            updateEvent.setCategoryId(oldEvent.getCategoryId());
        }
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
            updateEvent.setId(id);
            return updateEvent;
        });
        repository.saveAndFlush(editEvent);
        return modelMapper.map(editEvent, EditedEventDTO.class);
    }

    public void delete(Integer id) {
        repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                id + " does not exist !!!"));
        repository.deleteById(id);
    }

    private EventDTO convertEntityToDto(Event event) {
        EventDTO eventDTO = new EventDTO();
        eventDTO.setId(event.getId());
        eventDTO.setBookingName(event.getBookingName());
        eventDTO.setEventNotes(event.getEventNotes());
        eventDTO.setBookingEmail(event.getBookingEmail());
        eventDTO.setEventStartTime(event.getEventStartTime());
        return eventDTO;
    }
}
