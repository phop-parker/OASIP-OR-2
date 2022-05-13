package com.oasip.oasipservices.controllers;

import com.oasip.oasipservices.DTOS.EditedEventDTO;
import com.oasip.oasipservices.DTOS.EventDTO;
import com.oasip.oasipservices.entities.Event;
import com.oasip.oasipservices.repositories.EventRepository;
import com.oasip.oasipservices.services.EventService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventController {
    @Autowired
    EventService eventService;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("")
    public List<EventDTO> getAllSubject() {
        return eventService.getAllEvent();
    }

    @GetMapping("/{id}")
    public EventDTO getEventById(@PathVariable Integer id) {
        return eventService.getEventById(id);
    }

    @PostMapping("")
    public void Event(@RequestBody Event event) {
        eventService.save(event);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        eventRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND,
                        id + " does not exist !!!"));
        eventRepository.deleteById(id);
    }

    @PutMapping("/{id}")
    public Event update(@RequestBody Event updateEvent, @PathVariable Integer id) {
        Event event = eventRepository.findById(id)
                .map(e -> mapEvent(e, updateEvent))
                .orElseGet(() -> {
                    updateEvent.setId(id);
                    return updateEvent;
                });
        return eventRepository.saveAndFlush(event);
    }

    @PatchMapping("/{id}")
    public EditedEventDTO updateEvent(@RequestBody Event updateEvent, @PathVariable Integer id) {
        Event editEvent = eventRepository.findById(id).map(event -> {
                    event.setEventNotes(updateEvent.getEventNotes());
                    event.setEventStartTime(updateEvent.getEventStartTime());
                return event;}).orElseGet(() -> {
                    updateEvent.setId(id);
                    return updateEvent;
                });
        eventRepository.saveAndFlush(editEvent);
        return modelMapper.map(editEvent, EditedEventDTO.class);
    }



    private Event mapEvent(Event existingEvent , Event updateEvent){
        existingEvent.setBookingEmail(updateEvent.getBookingEmail());
        existingEvent.setCategoryId(updateEvent.getCategoryId());
        existingEvent.setBookingName(updateEvent.getBookingName());
        existingEvent.setEventDuration(updateEvent.getEventDuration());
        existingEvent.setEventStartTime(updateEvent.getEventStartTime());
        existingEvent.setEventNotes(updateEvent.getEventNotes());
        return existingEvent;
    }
}
