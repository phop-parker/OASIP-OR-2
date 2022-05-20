package com.oasip.oasipservices.repositories;

import com.oasip.oasipservices.entities.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface EventRepository extends JpaRepository<Event,Integer>{
    List<Event> findAllByOrderByEventStartTimeDesc();

    @Query(value = "select * from event e where e.categoryId = :categoryId and ((e.eventStartTime < :newEventStartDate  and :newEventStartDate < DATE_ADD(e.eventStartTime,INTERVAL e.eventDuration MINUTE)) or (e.eventStartTime < :newEventEndTime and :newEventEndTime < DATE_ADD(e.eventStartTime,INTERVAL e.eventDuration MINUTE)) or (:newEventStartDate  < e.eventStartTime and DATE_ADD(e.eventStartTime,INTERVAL e.eventDuration MINUTE) < :newEventEndTime))",
    nativeQuery = true)
    List<Event> findOverlappingEvents(Integer categoryId,LocalDateTime newEventStartDate, LocalDateTime newEventEndTime);


}