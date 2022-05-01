package com.oasip.oasipservices.controllers;

import com.oasip.oasipservices.entities.Event;
import com.oasip.oasipservices.repositories.EventRepository;
import com.oasip.oasipservices.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventController {

    @Autowired
    private EventService eventService;
    @Autowired
    private EventRepository repository;

    @GetMapping("")
    public List<Event> getAllCategories(@RequestParam(defaultValue = "eventStartTime") String sortBy) {
        if (sortBy.equals("eventStartTime")) {
            return repository.findAll(Sort.by(sortBy).descending());
        } else {
            return repository.findAll(Sort.by(sortBy).ascending());
        }
    }

    @PostMapping("")
    public Event create(@RequestBody Event newEvent) {
        return eventService.save(newEvent);
    }

    private Event mapEvent(Event existingEvent, Event updateEvent){
        existingEvent.setEventDuration(updateEvent.getEventDuration());
        existingEvent.setEventStartTime(updateEvent.getEventStartTime());
        existingEvent.setEventNotes(updateEvent.getEventNotes());
        existingEvent.setBookingEmail(updateEvent.getBookingEmail());
        existingEvent.setCategoryId(updateEvent.getCategoryId());
        existingEvent.setBookingName(updateEvent.getBookingName());
        return existingEvent;
    }

    @PutMapping("/{eventId}")
    public Event updateEmployee(@PathVariable Integer eventId, @RequestBody Event eventUpdated) {
        Event event = repository.findById(eventId).map(e -> mapEvent(e, eventUpdated))
                .orElseGet(() -> {
                    eventUpdated.setId(eventId);
                    return eventUpdated;
                });
        return repository.saveAndFlush(event);
    }

    @DeleteMapping("/{eventId}")
    public void delete(@PathVariable Integer eventId){
        repository.findById(eventId).orElseThrow(()->
        new ResponseStatusException(HttpStatus.NOT_FOUND));
        repository.deleteById(eventId);
    }

}
