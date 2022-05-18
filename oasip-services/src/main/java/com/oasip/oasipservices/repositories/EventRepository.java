package com.oasip.oasipservices.repositories;

import com.oasip.oasipservices.entities.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EventRepository extends JpaRepository<Event,Integer>{
    List<Event> findAllByOrderByEventStartTimeDesc();

    @Query("SELECT e FROM Event e JOIN EventCategory c ON e.category.categoryId = c.categoryId where c.categoryId = :categoryId")
    List<Event> findEventByCategoryName(Integer categoryId);
}