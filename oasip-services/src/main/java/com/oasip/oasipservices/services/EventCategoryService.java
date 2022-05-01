package com.oasip.oasipservices.services;

import com.oasip.oasipservices.entities.EventCategory;
import com.oasip.oasipservices.repositories.EventCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventCategoryService {

    @Autowired
    private EventCategoryRepository repository;

    public List<EventCategory> getAllCategories(){
        return repository.findAll((Sort.by("eventCategoryName").ascending()));
    }

}
