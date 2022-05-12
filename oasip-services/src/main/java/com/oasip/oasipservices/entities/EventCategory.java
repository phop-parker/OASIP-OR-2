package com.oasip.oasipservices.entities;

import javax.persistence.*;

@Table(name = "eventCategory", indexes = {
        @Index(name = "eventCategoryName", columnList = "eventCategoryName", unique = true)
})
@Entity
public class EventCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "categoryId", nullable = false)
    private Integer id;

    @Column(name = "eventCategoryName", nullable = false, length = 100)
    private String eventCategoryName;

    @Column(name = "eventCatagoryDescription", length = 400)
    private String eventCatagoryDescription;

    @Column(name = "eventDuration", nullable = false)
    private Integer eventDuration;

    public Integer getEventDuration() {
        return eventDuration;
    }

    public void setEventDuration(Integer eventDuration) {
        this.eventDuration = eventDuration;
    }

    public String getEventCatagoryDescription() {
        return eventCatagoryDescription;
    }

    public void setEventCatagoryDescription(String eventCatagoryDescription) {
        this.eventCatagoryDescription = eventCatagoryDescription;
    }

    public String getEventCategoryName() {
        return eventCategoryName;
    }

    public void setEventCategoryName(String eventCategoryName) {
        this.eventCategoryName = eventCategoryName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}