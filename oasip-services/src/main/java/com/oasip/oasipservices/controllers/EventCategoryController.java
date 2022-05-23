package com.oasip.oasipservices.controllers;

import com.oasip.oasipservices.DTOS.*;

import com.oasip.oasipservices.services.EventCategoryService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;



@RestController
@RequestMapping("/api/eventCategories")
public class EventCategoryController {

    @Autowired
    private EventCategoryService eventCategoryService;


    @GetMapping("")
    public List<EventCategoryDTO> getFullCategories() {
        return eventCategoryService.getFullCategories();
    }

    @GetMapping("/list")
    public List<EventCategoryView> getAllCategoriesList() {
        return eventCategoryService.getAllCategoriesList();
    }

    @GetMapping("/{categoryId}")
    public EventCategoryDTO getEventCategoryById(@PathVariable Integer categoryId) {
        return eventCategoryService.getAllCategoryById(categoryId);
    }

    @PatchMapping("/{categoryId}")
    public EventCategoryDTO updateEventCategory(@Valid @RequestBody EventCategoryDTO updateEventCategory, @PathVariable Integer categoryId) {
        return eventCategoryService.updateEventCategory(updateEventCategory,categoryId);}


}