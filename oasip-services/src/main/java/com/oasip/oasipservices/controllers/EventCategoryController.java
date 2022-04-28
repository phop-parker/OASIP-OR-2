package com.oasip.oasipservices.controllers;

import com.oasip.oasipservices.entities.Eventcategory;
import com.oasip.oasipservices.repositories.EventCategoryRepository;
import com.oasip.oasipservices.services.EventCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/eventCatagories")
public class EventCategoryController {

    @Autowired
    private EventCategoryService eventCategoryService;

    @Autowired
    private EventCategoryRepository repository;

    @GetMapping()
    public List<Eventcategory> getAllCategories(){
        return eventCategoryService.getAllCategories();
    }
//    private EventCategory mapCategory(EventCategory existingEventCategory, EventCategory updateEventCategory) {
//        existingEventCategory.setEventCategoryName(updateEventCategory.getEventCategoryName());
//        existingEventCategory.setEve  ntDuration(updateEventCategory.getEventDuration());
//        existingEventCategory.setEventCatagoryDescription(updateEventCategory.getEventCatagoryDescription());
//        return existingEventCategory;
//    }

        @GetMapping("/{catagortyId}")
    public Optional<Eventcategory> updateEmployee(@PathVariable Integer catagortyId) {
        return repository.findById(catagortyId);
    }

//    @PutMapping("/{catagortyId}")
//    public Event updateEmployee(@PathVariable Integer catagortyId, @RequestBody EventCategory eventCategoryUpdated) {
//        Event event = repository.findById(catagortyId).map(e -> mapCategory(e, eventCategoryUpdated))
//                .orElseGet(() -> {
//                    eventCategoryUpdated.setId(catagortyId);
//                    return eventCategoryUpdated;
//                });
//        return repository.saveAndFlush(event);
//    }

}
