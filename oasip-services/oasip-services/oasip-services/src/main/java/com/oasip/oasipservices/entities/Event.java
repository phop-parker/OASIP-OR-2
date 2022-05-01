package com.oasip.oasipservices.entities;

import javax.persistence.*;
import java.time.Instant;

@Table(name = "event", indexes = {
        @Index(name = "bookingName", columnList = "bookingName, eventStartTime", unique = true),
        @Index(name = "fk_event_eventCategory_idx", columnList = "categoryId")
})
@Entity
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "eventId", nullable = false)
    private Integer id;

    @Column(name = "bookingName", nullable = false)
    private String bookingName;

    @Column(name = "bookingEmail", nullable = false, length = 70)
    private String bookingEmail;

    @Column(name = "eventStartTime", nullable = false)
    private Instant eventStartTime;

    @Column(name = "eventDuration", nullable = false)
    private Integer eventDuration;

    @ManyToOne(optional = false)
    @JoinColumn(name = "categoryId", nullable = false)
    private EventCategory categoryId;

    @Column(name = "eventNotes", length = 500, nullable = true)
    private String eventNotes;

    public String getEventNotes() {
        return eventNotes;
    }

    public void setEventNotes(String eventNotes) {
        this.eventNotes = eventNotes;
    }

    public EventCategory getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(EventCategory categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getEventDuration() {
        return eventDuration;
    }

    public void setEventDuration(Integer eventDuration) {
        this.eventDuration = eventDuration;
    }

    public Instant getEventStartTime() {
        return eventStartTime;
    }

    public void setEventStartTime(Instant eventStartTime) {
        this.eventStartTime = eventStartTime;
    }

    public String getBookingEmail() {
        return bookingEmail;
    }

    public void setBookingEmail(String bookingEmail) {
        this.bookingEmail = bookingEmail;
    }

    public String getBookingName() {
        return bookingName;
    }

    public void setBookingName(String bookingName) {
        this.bookingName = bookingName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}