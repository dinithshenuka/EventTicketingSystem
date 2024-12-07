package lk.ac.iit.EventTicketingSystem.service;

import lk.ac.iit.EventTicketingSystem.dto.AddTicketDTO;
import lk.ac.iit.EventTicketingSystem.exception.UserNotFoundException;
import lk.ac.iit.EventTicketingSystem.models.Event;
import lk.ac.iit.EventTicketingSystem.models.Ticket;
import lk.ac.iit.EventTicketingSystem.repository.EventRepo;
import lk.ac.iit.EventTicketingSystem.repository.TicketRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class TicketService {
    private final TicketRepo ticketRepo;
    private final TicketPool ticketPool;
    private final EventRepo eventRepo;

    @Autowired
    public TicketService(TicketRepo ticketRepo, TicketPool ticketPool, EventRepo eventRepo) {
        this.ticketRepo = ticketRepo;
        this.ticketPool = ticketPool;
        this.eventRepo = eventRepo;
    }

    Logger logger = LoggerFactory.getLogger(TicketService.class);

    // method to add tickets (Vendor add tickets)
    @Async(value = "treadPool")
    public CompletableFuture<List<Ticket>> addTicket(AddTicketDTO addTicketDTO) {
        List<Ticket> savedTickets = new ArrayList<>();
        Ticket baseTicket = addTicketDTO.getTicket();
        Event event = addTicketDTO.getEvent();
        Long eventId = event.getEventId();
        int ticketCount = addTicketDTO.getTicketCount();
        try {
            for (int loop = 0; loop < ticketCount; loop++) {

                Ticket ticket = new Ticket();

                ticket.setEvent(event); // composition

                ticket.setTicketCode(UUID.randomUUID().toString());
                ticket.setTicketPrice(baseTicket.getTicketPrice());
                ticket.setTicketType(baseTicket.getTicketType());
                ticket.setTicketStatus(baseTicket.getTicketStatus());

                ticketRepo.save(ticket); // Save to DB
                ticketPool.addToTicketPool(eventId,ticket); // Add to the pool
                logger.info("Ticket saved to the Database and TicketPool: {}", ticket);

                Thread.sleep(1000);

                // for api return (adding to saved tickets array)
                savedTickets.add(ticket);
            }
        } catch (InterruptedException e) {
            logger.error("Thread was interrupted", e);
            Thread.currentThread().interrupt();
            throw new RuntimeException("Thread was interrupted", e);
        }
        return CompletableFuture.completedFuture(savedTickets); // api
    }

    // method to remove tickets (Customer buy tickets)
    @Async(value = "treadPool")
    @Transactional
    public CompletableFuture<Ticket> buyTicket(Long eventId) {
        return CompletableFuture.supplyAsync(() -> {
            Event foundEvent = eventRepo.findById(eventId)
                    .orElseThrow(() -> new UserNotFoundException("Event by id " + eventId + " was not found"));
            logger.info("Event found: {}", foundEvent);

            Ticket foundTicket;
            try {
                foundTicket = ticketPool.removeFromTicketPool(eventId);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            synchronized (foundTicket) {
                foundTicket.setTicketStatus("booked");
                ticketRepo.save(foundTicket);

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    throw new RuntimeException("Thread was interrupted", e);
                }

                logger.info("Ticket booked successfully: {}", foundTicket);
                logger.info("Ticket removed from the pool: {}", foundTicket);
                logger.info("Ticket status updated in the database: {}", foundTicket);

                return foundTicket;
            }
        });
    }

    public List<Ticket> findAllTickets() {
        return ticketRepo.findAll();
    }

    public Ticket updateTicket(Ticket ticket) {
        return ticketRepo.save(ticket);
    }

    public Ticket findTicketById(Long ticketId) {
        return ticketRepo.findById(ticketId)
                .orElseThrow(() -> new UserNotFoundException("User by id " + ticketId + " was not found"));
    }

    public void deleteTicket(Long ticketId) {
        ticketRepo.deleteById(ticketId);
    }
}
