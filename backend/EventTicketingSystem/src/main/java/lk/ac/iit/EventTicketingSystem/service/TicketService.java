package lk.ac.iit.EventTicketingSystem.service;

import lk.ac.iit.EventTicketingSystem.dto.AddTicketDTO;
import lk.ac.iit.EventTicketingSystem.exception.UserNotFoundException;
import lk.ac.iit.EventTicketingSystem.models.Ticket;
import lk.ac.iit.EventTicketingSystem.repository.TicketRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
public class TicketService {
    private final TicketRepo ticketRepo;

    @Autowired
    public TicketService(TicketRepo ticketRepo) {
        this.ticketRepo = ticketRepo;
    }

    Logger logger = (Logger) LoggerFactory.getLogger(TicketService.class);

    @Async
    public CompletableFuture<Ticket> addTicket(AddTicketDTO addTicketDTO) {
       
        Ticket ticket = addTicketDTO.getTicket();
        int ticketCount = addTicketDTO.getTicketCount();
        ticket.setTicketCode(UUID.randomUUID().toString());

            // Save the Ticket object to the database
        Ticket savedTicket = ticketRepo.save(ticket);

            // Handle ticketCount logic (e.g., adding to a pool or maintaining a cache)
        logger.info("Ticket Count: {}", ticketCount);
            // Example: Adding logic for pool management (not database related)

        return CompletableFuture.completedFuture(ticket);
    }



//    public Ticket addTicket(Ticket ticket) {
//        ticket.setTicketCode(UUID.randomUUID().toString());
//        return ticketRepo.save(ticket);
//    }

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
