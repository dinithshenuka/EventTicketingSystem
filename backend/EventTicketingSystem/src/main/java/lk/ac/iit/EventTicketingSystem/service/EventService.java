package lk.ac.iit.EventTicketingSystem.service;

import lk.ac.iit.EventTicketingSystem.exception.UserNotFoundException;
import lk.ac.iit.EventTicketingSystem.models.Event;
import lk.ac.iit.EventTicketingSystem.repository.EventRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class EventService {
    private final EventRepo eventRepo;

    @Autowired
    public EventService(EventRepo eventRepo) {
        this.eventRepo = eventRepo;
    }

    // basic crud

    public Event addEvent(Event event) {
        event.setEventCode(UUID.randomUUID().toString());
        return eventRepo.save(event);
    }

    public List<Event> findAllEvents() {
        return eventRepo.findAll();
    }

    public Event updateEvent(Event event) {
        return eventRepo.save(event);
    }

    public Event findEventById(Long eventId) {
        return eventRepo.findById(eventId)
                .orElseThrow(() -> new UserNotFoundException("User by id " + eventId + " was not found"));
    }

    public void deleteEvent(Long eventId) {
        eventRepo.deleteById(eventId);
    }

}
