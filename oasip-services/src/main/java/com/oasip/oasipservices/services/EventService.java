package com.oasip.oasipservices.services;


import com.oasip.oasipservices.entities.Event;
import com.oasip.oasipservices.repositories.EventRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventService {

    @Autowired
    private EventRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    public Event save(Event newEvent) {
        Event event = modelMapper.map(newEvent, Event.class);
        return repository.saveAndFlush(event);    }

}