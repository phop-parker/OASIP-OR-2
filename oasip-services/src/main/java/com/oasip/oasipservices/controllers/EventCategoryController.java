package com.oasip.oasipservices.controllers;

import com.oasip.oasipservices.DTOS.EventBookingCategoryDTO;
import com.oasip.oasipservices.DTOS.EventCategoriesDTO;
import com.oasip.oasipservices.entities.EventCategory;
import com.oasip.oasipservices.repositories.EventCategoryRepository;
import com.oasip.oasipservices.services.EventCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/eventCategories")
public class EventCategoryController {

    @Autowired
    private EventCategoryService eventCategoryService;

    @Autowired
    private EventCategoryRepository repository;

    @GetMapping()
    public List<EventCategoriesDTO> getFullCategories () {
        return eventCategoryService.getFullCategories();
    }

    @GetMapping("/forBooking")
    public List<EventBookingCategoryDTO> getAllCategoriesForBooking(){
        return eventCategoryService.getAllCategoriesForBooking();
    }

    @GetMapping("/{catagortyId}")
    public Optional<EventCategory> updateEmployee(@PathVariable Integer catagortyId) {
        return repository.findById(catagortyId);
    }

}
