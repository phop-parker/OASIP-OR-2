package com.oasip.oasipservices.services;

import com.oasip.oasipservices.DTOS.EventBookingCategoryDTO;
import com.oasip.oasipservices.DTOS.EventCategoriesDTO;
import com.oasip.oasipservices.DTOS.EventDTO;
import com.oasip.oasipservices.entities.Event;
import com.oasip.oasipservices.entities.EventCategory;
import com.oasip.oasipservices.repositories.EventCategoryRepository;
import com.oasip.oasipservices.utils.ListMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class EventCategoryService {

    @Autowired
    private EventCategoryRepository repository;

    @Autowired
    private ListMapper listMapper;

    @Autowired
    private ModelMapper modelMapper;


    public List<EventBookingCategoryDTO> getAllCategoriesForBooking() {
        List<EventCategory> eventList = repository.findAll((Sort.by("eventCategoryName").ascending()));
        return listMapper.mapList(eventList, EventBookingCategoryDTO.class, modelMapper);
    }

    public List<EventCategoriesDTO> getFullCategories() {
        List<EventCategory> eventList = repository.findAll((Sort.by("eventCategoryName").ascending()));
        return listMapper.mapList(eventList, EventCategoriesDTO.class, modelMapper);
    }

    public EventCategoriesDTO getAllCategoryById (Integer id){
        EventCategory event = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Event id " + id +
                        "Does Not Exist !!!"));
        return modelMapper.map(event, EventCategoriesDTO.class);
    }

}
