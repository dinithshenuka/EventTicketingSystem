package lk.ac.iit.EventTicketingSystem.service;

import lk.ac.iit.EventTicketingSystem.dto.AddTicketDTO;
import lk.ac.iit.EventTicketingSystem.dto.BuyTicketDTO;
import lk.ac.iit.EventTicketingSystem.exception.UserNotFoundException;
import lk.ac.iit.EventTicketingSystem.models.Customer;
import lk.ac.iit.EventTicketingSystem.models.Ticket;
import lk.ac.iit.EventTicketingSystem.repository.TicketRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@Service
public class TicketService {
    private final TicketRepo ticketRepo;
    private final TicketPool ticketPool;

    @Autowired
    public TicketService(TicketRepo ticketRepo, TicketPool ticketPool) {
        this.ticketRepo = ticketRepo;
        this.ticketPool = ticketPool;
    }

    Logger logger = LoggerFactory.getLogger(TicketService.class);

    // method to add tickets (Vendor add tickets)
    @Async(value = "treadPool")
    public CompletableFuture<List<Ticket>> addTicket(AddTicketDTO addTicketDTO) {
        List<Ticket> savedTickets = new ArrayList<>();
        Ticket baseTicket = addTicketDTO.getTicket();
        int ticketCount = addTicketDTO.getTicketCount();
        try {
            for (int loop = 0; loop < ticketCount; loop++) {

                Ticket ticket = new Ticket();

                ticket.setTicketCode(UUID.randomUUID().toString());
                ticket.setTicketPrice(baseTicket.getTicketPrice());
                ticket.setTicketType(baseTicket.getTicketType());
                ticket.setTicketStatus(baseTicket.getTicketStatus());

                ticketRepo.save(ticket); // Save to DB
                ticketPool.addToTicketPool(ticket); // Add to the pool
                logger.info("Ticket saved to the Database and TicketPool: {}", ticket);

                Thread.sleep(2000);

                // for api return
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
    public CompletableFuture<Ticket> buyTicket(Long ticketId) {
        return CompletableFuture.supplyAsync(() -> {
            Ticket foundTicket = ticketRepo.findById(ticketId)
                    .orElseThrow(() -> new UserNotFoundException("Ticket by id " + ticketId + " was not found"));
            logger.info("Ticket found: {}", foundTicket);

            synchronized (foundTicket) {
                if (foundTicket.getTicketStatus().equals("available")) {
                    foundTicket.setTicketStatus("booked");

                    ticketRepo.save(foundTicket);
                    ticketPool.removeFromTicketPool(foundTicket);

                    logger.info("Ticket booked: {}", foundTicket);
                } else {
                    throw new IllegalStateException("Ticket is already booked");
                }
            }
            return foundTicket;
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
