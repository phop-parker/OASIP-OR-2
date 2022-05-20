package com.oasip.oasipservices.controllers;

import com.oasip.oasipservices.DTOS.*;
import com.oasip.oasipservices.entities.Event;
import com.oasip.oasipservices.entities.EventCategory;
import com.oasip.oasipservices.repositories.EventCategoryRepository;
import com.oasip.oasipservices.services.EventCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public List<EventCategoryDTO> getFullCategories() {
        return eventCategoryService.getFullCategories();
    }

    @GetMapping("/forBooking")
    public List<EventBookingCategoryDTO> getAllCategoriesForBooking() {
        return eventCategoryService.getAllCategoriesForBooking();
    }

    @GetMapping("/{categoryId}")
    public EventCategoryDTO getEventCategoryById(@PathVariable Integer categoryId) {
        return eventCategoryService.getAllCategoryById(categoryId);
    }

    @PatchMapping("/{categoryId}")
    public EventCategory updateEventCategory(@Valid @RequestBody EventCategoryDTO updateEventCategory, @PathVariable Integer categoryId) {
        return eventCategoryService.updateEventCategory(updateEventCategory,categoryId);}


}