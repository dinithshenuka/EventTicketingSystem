package lk.ac.iit.EventTicketingSystem.controller;

import lk.ac.iit.EventTicketingSystem.models.Event;
import lk.ac.iit.EventTicketingSystem.service.EventService;
import lk.ac.iit.EventTicketingSystem.service.TicketPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/event")
public class EventController {
    private final EventService eventService;
    private final TicketPool ticketPool;

    @Autowired
    public EventController(EventService eventService, TicketPool ticketPool) {
        this.eventService = eventService;
        this.ticketPool = ticketPool;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Event>> getAllEvents() {
        List<Event> events = eventService.findAllEvents();
        return new ResponseEntity<>(events, HttpStatus.OK);
    }

    @GetMapping("/find/{eventId}")
    public ResponseEntity<Event> getEventById(@PathVariable Long eventId) {
        Event event= eventService.findEventById(eventId);
        return new ResponseEntity<>(event, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Event> addEvent(@RequestBody Event event) {
        Event newEvent = eventService.addEvent(event);
        return new ResponseEntity<>(newEvent, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<Event> updateEvent(@RequestBody Event event) {
        Event updatedEvent = eventService.updateEvent(event);
        return new ResponseEntity<>(updatedEvent, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{eventId}")
    public ResponseEntity<?> deleteEvent(@PathVariable Long eventId) {
        eventService.deleteEvent(eventId);
        ticketPool.removeEvent(eventId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}