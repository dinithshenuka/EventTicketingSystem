// src/main/java/lk/ac/iit/EventTicketingSystem/service/TicketService.java
package lk.ac.iit.EventTicketingSystem.service;

import lk.ac.iit.EventTicketingSystem.SystemConfiguration;
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

@Service
public class TicketService {
    private final TicketRepo ticketRepo;
    private final TicketPool ticketPool;
    private final CustomerService customerService;
    private final SystemConfiguration systemConfiguration;

    @Autowired
    public TicketService(TicketRepo ticketRepo, TicketPool ticketPool, CustomerService customerService, SystemConfiguration systemConfiguration) {
        this.ticketRepo = ticketRepo;
        this.ticketPool = ticketPool;
        this.customerService = customerService;
        this.systemConfiguration = systemConfiguration;
    }

    Logger logger = LoggerFactory.getLogger(TicketService.class);

    // method to add tickets (Vendor add tickets)
    @Async(value = "VendorThreadPool")
    public CompletableFuture<List<Ticket>> addTicket(AddTicketDTO addTicketDTO) {
        if (!systemConfiguration.isRunning()) {
            throw new IllegalStateException("System is stopped. Cannot add tickets.");
        }

        List<Ticket> savedTickets = new ArrayList<>();
        Ticket baseTicket = addTicketDTO.getTicket();
        Event event = addTicketDTO.getEvent();
        Vendor vendor = addTicketDTO.getVendor();

        logger.info("Thread {} is allocated to Vendor {}",
                Thread.currentThread().getName().replaceAll("\\D+", ""), vendor.getVendorId());

        logger.info("ticketReleaseRate : {} milliseconds", systemConfiguration.getTicketReleaseRate());

        int ticketCount = addTicketDTO.getTicketCount();
        try {
            for (int loop = 0; loop < ticketCount; loop++) {
                Ticket ticket = new Ticket();
                ticket.setEvent(event);
                ticket.setVendor(vendor);
                ticket.setTicketCode(UUID.randomUUID().toString());
                ticket.setTicketPrice(baseTicket.getTicketPrice());
                ticket.setTicketType(baseTicket.getTicketType());
                ticket.setTicketStatus("available");

                ticketRepo.save(ticket);
                ticketPool.addToTicketPool(event.getEventId(), ticket);
                logger.info("Ticket saved to the Database");

                Thread.sleep(1000L * systemConfiguration.getTicketReleaseRate());
            }
        } catch (InterruptedException e) {
            logger.error("Thread was interrupted", e);
            Thread.currentThread().interrupt();
            throw new RuntimeException("Thread was interrupted", e);
        }
        return CompletableFuture.completedFuture(savedTickets);
    }

    // method to remove tickets (Customer buy tickets)
    @Async(value = "CustomerThreadPool")
    @Transactional
    public CompletableFuture<ResponseEntity<List<Ticket>>> buyTicket(Long eventId, BuyTicketDTO buyTicketDTO) {
        if (!systemConfiguration.isRunning()) {
            throw new IllegalStateException("System is stopped. Cannot buy tickets.");
        }

        int ticketCount = buyTicketDTO.getTicketCount();
        Customer customer = customerService.findOrAddCustomer(
                buyTicketDTO.getCustomer().getFirstName(),
                buyTicketDTO.getCustomer().getEmail(),
                buyTicketDTO.getCustomer().getPhone()
        );

        logger.info("Thread {} is allocated to Customer {}",
                Thread.currentThread().getName().replaceAll("\\D+", ""), customer.getCustomerId());

        logger.info("customerRetrievalRate : {} milliseconds", systemConfiguration.getCustomerRetrievalRate());

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
            for (int loop = 0; loop < ticketCount; loop++) {
                Ticket ticket = ticketPool.removeFromTicketPool(eventId);
                ticket.setTicketStatus("booked");
                ticket.setCustomer(customer);

                ticketRepo.save(ticket);
                bookedTickets.add(ticket);
                logger.info("Ticket booked successfully: {}", ticket.getTicketId());

                Thread.sleep(1000L * systemConfiguration.getCustomerRetrievalRate());
                logger.info("Thread {} is sleeping for {} seconds",
                        Thread.currentThread().getName().replaceAll("\\D+", ""), systemConfiguration.getCustomerRetrievalRate());
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