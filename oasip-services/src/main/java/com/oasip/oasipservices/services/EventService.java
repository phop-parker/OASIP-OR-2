package com.oasip.oasipservices.services;

import com.oasip.oasipservices.DTOS.EditedEventDTO;
import com.oasip.oasipservices.DTOS.EventDTO;
import com.oasip.oasipservices.entities.Event;
import com.oasip.oasipservices.entities.EventCategory;
import com.oasip.oasipservices.repositories.EventRepository;
import com.oasip.oasipservices.utils.ListMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.regex.Pattern;

@Service

public class EventService {

    @Autowired
    private ListMapper listMapper;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private EventRepository repository;

    public Event save(Event event) {
        checkDateTimeFuture(event.getEventStartTime(), event.getCategoryId().getEventCategoryName(),
                event.getCategoryId().getEventDuration());
        checkBookingName(event.getBookingName());
        checkEventNotes(event.getEventNotes());
        checkBookingEmail(event.getBookingEmail());
        checkEventDuration(event.getEventDuration());
        checkEventCategory(event.getCategoryId());
        return repository.saveAndFlush(event);
    }

    public List<EventDTO> getAllEvent() {
        List<Event> eventList = repository.findAllByOrderByEventStartTimeDesc();
        return listMapper.mapList(eventList, EventDTO.class, modelMapper);
    }

    public EventDTO getEventById(Integer id) {
        Event event = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Event id " + id +
                                "Does Not Exist !!!"));
        return modelMapper.map(event, EventDTO.class);
    }

    private void checkDateTimeFuture(LocalDateTime updateDateTime, String newEventCategoryName,
            Integer newEventDuration) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        LocalDateTime newEventStartTime = updateDateTime;
        LocalDateTime newEventEndTime = findEndDate(newEventStartTime, newEventDuration);
        List<EventDTO> eventList = getAllEvent();
        for (int i = 0; i < eventList.size(); i++) {
            // System.out.println("for loop process number"+i);
            LocalDateTime eventStartTime = eventList.get(i).getEventStartTime();
            LocalDateTime eventEndTime = findEndDate(eventList.get(i).getEventStartTime(),
                    eventList.get(i).getEventDuration());
            String eventCategory = eventList.get(i).getEventCategoryName();
            // System.out.println("this is new cate"+newEventCategoryName);
            // System.out.println("this is event cate"+i+" "+eventCategory);
            // System.out.println("eventCategory.equalsIgnoreCase(newEventCategoryName)
            // "+eventCategory.equalsIgnoreCase(newEventCategoryName));
            if (eventCategory.equalsIgnoreCase(newEventCategoryName)) {
                // System.out.println("same category check");
                if (newEventStartTime.isBefore(eventStartTime) && newEventEndTime.isAfter(eventStartTime) ||
                        newEventStartTime.isBefore(eventEndTime) && newEventEndTime.isAfter(eventEndTime) ||
                        newEventStartTime.isBefore(eventStartTime) && newEventEndTime.isAfter(eventEndTime) ||
                        newEventStartTime.isAfter(eventStartTime) && newEventEndTime.isBefore(eventEndTime)) {
                    // System.out.println("time is overlapping");
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                            "Time is overlapping!! change date-time please");
                }
            }
        }
        if (newEventStartTime == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "updateEvent cannot be null");
        } else if (newEventStartTime.isBefore(currentDateTime)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "please insert future datetime");
        }
    }

    private LocalDateTime findEndDate(LocalDateTime eventStartTime, Integer duration) {
        LocalDateTime getEventEndTime = eventStartTime.plusMinutes(duration);
        return getEventEndTime;
    }

    private void checkBookingName(String bookingName) {
        if (bookingName == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "bookingName cannot be null");
        } else if (bookingName.length() > 100) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "bookingName is to long");
        }
    }

    private boolean validateEmail(String bookingEmail) {
        String pattern = "^(.+)@(\\S+).(\\S+)$";
        return Pattern.compile(pattern)
                .matcher(bookingEmail)
                .matches();
    }

    private void checkBookingEmail(String bookingEmail) {
        if (bookingEmail == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "bookingEmail cannot be null");
        } else if (validateEmail(bookingEmail) == false) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wrong email format");
        }
    }

    private void checkEventNotes(String eventNotes) {
     if (eventNotes.length() > 500) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "eventNotes is to long");
        }
    }

    private void checkEventDuration(Integer eventDuration) {
        if (eventDuration == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "eventNotes cannot be null");
        } else if (eventDuration == 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "eventDuration should be morethan 0 minutes");
        }
    }

    private void checkEventCategory(EventCategory categoryId) {
        if (categoryId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "eventCategory cannot be null");
        } else if (categoryId.getId() == 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot find event category");
        }
    }

    public EditedEventDTO updateEvent(Event updateEvent, Integer id) {
        checkEventNotes(updateEvent.getEventNotes());
        Event editEvent = repository.findById(id).map(event -> {
            event.setEventNotes(updateEvent.getEventNotes());
            event.setEventStartTime(updateEvent.getEventStartTime());
            return event;
        }).orElseGet(() -> {
            updateEvent.setId(id);
            return updateEvent;
        });
        repository.saveAndFlush(editEvent);
        return modelMapper.map(editEvent, EditedEventDTO.class);
    }

    public void delete(Integer id) {
        repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                id + " does not exist !!!"));
        repository.deleteById(id);
    }

    private EventDTO convertEntityToDto(Event event) {
        EventDTO eventDTO = new EventDTO();
        eventDTO.setId(event.getId());
        eventDTO.setBookingName(event.getBookingName());
        eventDTO.setEventNotes(event.getEventNotes());
        eventDTO.setBookingEmail(event.getBookingEmail());
        eventDTO.setEventStartTime(event.getEventStartTime());
        return eventDTO;
    }
}
