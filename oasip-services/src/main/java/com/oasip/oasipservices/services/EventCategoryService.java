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

    public List<EventCategoryView> getAllCategoriesList() {
        List<EventCategory> eventList = repository.findAll((Sort.by("categoryId").descending()));
        return listMapper.mapList(eventList, EventCategoryView.class, modelMapper);
    }

    public List<EventCategoryDTO> getFullCategories() {
        List<EventCategory> eventList = repository.findAll((Sort.by("categoryId").descending()));
        return listMapper.mapList(eventList, EventCategoryDTO.class, modelMapper);
    }

    public EventCategoryDTO getAllCategoryById (Integer id){
        EventCategory event = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Event id " + id +
                        "Does Not Exist !!!"));
        return modelMapper.map(event, EventCategoryDTO.class);
    }

    public void checkCategory(Integer categoryId,String categoryName){
        List<EventCategory> sameNameCategory = repository.findSameCategoryName(categoryId,categoryName);
        if(sameNameCategory.size()>=1){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Category name cannot be the same");
        }
    }
    public EventCategoryDTO updateEventCategory(EventCategoryDTO updatedEventCategory,Integer id){
        EventCategory oldEventCategory = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Event id " + id +
                        "Does Not Exist !!!"));
        checkCategory(id,updatedEventCategory.getEventCategoryName());
        if(updatedEventCategory.getEventCategoryName() == null ){
            updatedEventCategory.setEventCategoryName(oldEventCategory.getEventCategoryName());
        }
        if(updatedEventCategory.getEventCategoryDescription() == null ){
            updatedEventCategory.setEventCategoryDescription(oldEventCategory.getEventCategoryDescription());
        }
        if(updatedEventCategory.getEventDuration() == null ){
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
        return modelMapper.map(editEventCategory,EventCategoryDTO.class);
    }

}