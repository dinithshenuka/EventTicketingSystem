package lk.ac.iit.EventTicketingSystem.service;

import lk.ac.iit.EventTicketingSystem.dto.AddTicketDTO;
import lk.ac.iit.EventTicketingSystem.dto.BuyTicketDTO;
import lk.ac.iit.EventTicketingSystem.exception.UserNotFoundException;
import lk.ac.iit.EventTicketingSystem.models.Customer;
import lk.ac.iit.EventTicketingSystem.models.Event;
import lk.ac.iit.EventTicketingSystem.models.Ticket;
import lk.ac.iit.EventTicketingSystem.models.Vendor;
import lk.ac.iit.EventTicketingSystem.repository.TicketRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    private final CustomerService customerService;

    @Autowired
    public TicketService(TicketRepo ticketRepo, TicketPool ticketPool, CustomerService customerService) {
        this.ticketRepo = ticketRepo;
        this.ticketPool = ticketPool;
        this.customerService = customerService;
    }

    Logger logger = LoggerFactory.getLogger(TicketService.class);

    // method to add tickets (Vendor add tickets)
    @Async(value = "VendorThreadPool")
    public CompletableFuture<List<Ticket>> addTicket(AddTicketDTO addTicketDTO) {

        List<Ticket> savedTickets = new ArrayList<>();

        Ticket baseTicket = addTicketDTO.getTicket();
        Event event = addTicketDTO.getEvent();
        Long eventId = event.getEventId();
        Vendor vendor = addTicketDTO.getVendor();
        Long vendorId = vendor.getVendorId();

        logger.info("Thread {} is allocated to Vendor {}",
                Thread.currentThread().getName().replaceAll("\\D+", ""), vendorId);

        int ticketCount = addTicketDTO.getTicketCount();
        try {
            for (int loop = 0; loop < ticketCount; loop++) {

                Ticket ticket = new Ticket();

                ticket.setEvent(event); // composition
                ticket.setVendor(vendor); // aggregation

                ticket.setTicketCode(UUID.randomUUID().toString());
                ticket.setTicketPrice(baseTicket.getTicketPrice());
                ticket.setTicketType(baseTicket.getTicketType());
                ticket.setTicketStatus("available");

                ticketRepo.save(ticket); // Save to DB
                ticketPool.addToTicketPool(eventId,ticket); // Add to the pool
                logger.info("Ticket saved to the Database");

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

    // method to remove tickets (Customer buy tickets
    @Async(value = "CustomerThreadPool")
    @Transactional
    public CompletableFuture<ResponseEntity<List<Ticket>>> buyTicket(Long eventId, BuyTicketDTO buyTicketDTO) {

        int ticketCount = buyTicketDTO.getTicketCount();

        Customer customer = customerService.findOrAddCustomer(
                buyTicketDTO.getCustomer().getFirstName(),
                buyTicketDTO.getCustomer().getEmail(),
                buyTicketDTO.getCustomer().getPhone()
        );

        logger.info("Thread {} is allocated to Customer {}",
                Thread.currentThread().getName().replaceAll("\\D+", ""), customer.getCustomerId());

        // Validate ticket count upfront
        int availableTickets = ticketPool.getTicketCountForEvent(eventId);
        if (availableTickets < ticketCount) {
            String errorMessage = String.format(
                    "Insufficient tickets available for event %d. Requested: %d, Available: %d",
                    eventId, ticketCount, availableTickets
            );
            logger.error(errorMessage);
            throw new IllegalArgumentException(errorMessage);
        }

        List<Ticket> bookedTickets = new ArrayList<>();
        try {
            // Attempt to book tickets
            for (int loop = 0; loop < ticketCount; loop++) {
                Ticket ticket = ticketPool.removeFromTicketPool(eventId);
                ticket.setTicketStatus("booked");
                ticket.setCustomer(customer);

                // Save the ticket status to the database
                ticketRepo.save(ticket);

                bookedTickets.add(ticket);
                logger.info("Ticket booked successfully: {}", ticket.getTicketId());

                Thread.sleep(1000);
            }

            logger.info("All requested tickets booked successfully for customer: {}", customer.getCustomerId());
        } catch (Exception e) {
            logger.error("Error occurred while booking tickets for customer: {}", customer, e);
            throw new RuntimeException("Failed to book tickets. Transaction rolled back.", e);
        }

        return CompletableFuture.completedFuture(ResponseEntity.ok(bookedTickets));
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
