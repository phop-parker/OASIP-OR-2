package com.oasip.oasipservices.repositories;

import com.oasip.oasipservices.entities.EventCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventCategoryRepository extends JpaRepository<EventCategory, Integer> {
}
