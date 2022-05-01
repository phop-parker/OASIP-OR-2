package com.oasip.oasipservices.repositories;

import com.oasip.oasipservices.entities.Eventcategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventCategoryRepository extends JpaRepository<Eventcategory, Integer> {
}
