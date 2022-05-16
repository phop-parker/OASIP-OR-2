package com.oasip.oasipservices.controllers;

import com.oasip.oasipservices.DTOS.CreateNewEventDTO;
import com.oasip.oasipservices.DTOS.EditedEventDTO;
import com.oasip.oasipservices.DTOS.EventDTO;
import com.oasip.oasipservices.entities.Event;
import com.oasip.oasipservices.repositories.EventRepository;
import com.oasip.oasipservices.services.EventService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventController {
    @Autowired
    EventService eventService;

    @GetMapping("")
    public List<EventDTO> getAllSubject() {
        return eventService.getAllEvent();
    }

    @GetMapping("/{id}")
    public EventDTO getEventById(@PathVariable Integer id) {
        return eventService.getEventById(id);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Event createNewEvent(@Validated @RequestBody CreateNewEventDTO event) {
        return eventService.save(event);}


    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) { eventService.delete(id);}

    @PatchMapping("/{id}")
    public EditedEventDTO updateEvent(@RequestBody Event updateEvent, @PathVariable Integer id) {return eventService.updateEvent(updateEvent,id);}

}
