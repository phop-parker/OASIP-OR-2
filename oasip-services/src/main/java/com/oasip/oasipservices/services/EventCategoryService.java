package com.oasip.oasipservices.services;

import com.oasip.oasipservices.DTOS.*;
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

    public List<EventCategoryDTO> getFullCategories() {
        List<EventCategory> eventList = repository.findAll((Sort.by("eventCategoryName").ascending()));
        return listMapper.mapList(eventList, EventCategoryDTO.class, modelMapper);
    }

    public EventCategoryDTO getAllCategoryById (Integer id){
        EventCategory event = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Event id " + id +
                        "Does Not Exist !!!"));
        return modelMapper.map(event, EventCategoryDTO.class);
    }

    public EventCategory updateEventCategory(EventCategoryDTO updatedEventCategory,Integer id){
        EventCategory oldEventCategory = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Event id " + id +
                        "Does Not Exist !!!"));
        if(updatedEventCategory.getEventCategoryName() == null ){
            updatedEventCategory.setEventCategoryName(oldEventCategory.getEventCategoryName());
        }else if(updatedEventCategory.getEventCategoryName() == null ){
            updatedEventCategory.setEventCategoryName(oldEventCategory.getEventCategoryName());
        }else if(updatedEventCategory.getEventDuration() == null ){
            updatedEventCategory.setEventDuration(oldEventCategory.getEventDuration());
        }

        EventCategory editEventCategory = repository.findById(id).map(eventCategory -> {
            eventCategory.setEventCategoryName(updatedEventCategory.getEventCategoryName());
            eventCategory.setEventDuration(updatedEventCategory.getEventDuration());
            eventCategory.setEventCategoryDescription(updatedEventCategory.getEventCategoryDescription());
            return eventCategory;
        }).orElseGet(() -> {
            updatedEventCategory.setCategoryId(id);
            return  modelMapper.map(updatedEventCategory, EventCategory.class);
        });
        repository.saveAndFlush(editEventCategory);
        return editEventCategory;
    }

}