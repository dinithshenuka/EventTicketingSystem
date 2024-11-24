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

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
public class TicketService {
    private final TicketRepo ticketRepo;
    private final TicketPool ticketPool;

    @Autowired
    public TicketService(TicketRepo ticketRepo, TicketPool ticketPool) {
        this.ticketRepo = ticketRepo;
        this.ticketPool = new TicketPool();
    }

    Logger logger = (Logger) LoggerFactory.getLogger(TicketService.class);

    @Async(value = "treadPool")
    public CompletableFuture<List<Ticket>> addTicket(AddTicketDTO addTicketDTO) {

        List<Ticket> savedTickets = new ArrayList<>();
        Ticket baseTicket = addTicketDTO.getTicket();
        int ticketCount = addTicketDTO.getTicketCount();

        try {
            for (int loop = 0; loop < ticketCount; loop++) {
                Ticket ticket = new Ticket(); // Create a new instance
                ticket.setTicketCode(UUID.randomUUID().toString());
                ticket.setEventDate(baseTicket.getEventDate());
                ticket.setEventName(baseTicket.getEventName());

                ticketRepo.save(ticket); // Save to DB
                logger.info("Ticket saved to Database: {}", ticket);
                ticketPool.addToTicketPool(ticket); // Add to the pool
                logger.info("Ticket saved to the Pool: {}", ticket);

                Thread.sleep(2000);

                savedTickets.add(ticket); // Add to the list of saved tickets
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        return CompletableFuture.completedFuture(savedTickets); // Return all created tickets
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
