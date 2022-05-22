package com.oasip.oasipservices.repositories;

import com.oasip.oasipservices.entities.Event;
import com.oasip.oasipservices.entities.EventCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EventCategoryRepository extends JpaRepository<EventCategory, Integer> {
    @Query(value = "select * from eventCategory e where e.categoryId != :categoryId and e.eventCategoryName = :eventCategoryName",
    nativeQuery = true)
    List<EventCategory> findSameCategoryName(Integer categoryId, String eventCategoryName);

    @Query(value="select c.categoryId, c.eventCategoryName, c.eventCategoryDescription, c.eventDuration " +
            "from eventCategory c LEFT JOIN event e on c.categoryId = e.categoryId where e.eventId = :id",
            nativeQuery = true)
    EventCategory findEventCategoryByEventId(Integer id);

}
