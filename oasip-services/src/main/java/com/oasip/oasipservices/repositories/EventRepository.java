package com.oasip.oasipservices.repositories;

import com.oasip.oasipservices.entities.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventRepository extends JpaRepository<Event,Integer>{
    List<Event> findAllByOrderByEventStartTimeDesc();
}
