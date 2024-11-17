package lk.ac.iit.EventTicketingSystem.service;

import lk.ac.iit.EventTicketingSystem.models.Ticket;
import lk.ac.iit.EventTicketingSystem.models.Vendor;
import lk.ac.iit.EventTicketingSystem.repository.TicketRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TicketService {
    private final TicketRepo ticketRepo;

    @Autowired
    public TicketService(TicketRepo ticketRepo) {
        this.ticketRepo = ticketRepo;
    }

    public Ticket addTicket(Ticket ticket) {
        ticket.setTicketCode(UUID.randomUUID().toString());
        return ticketRepo.save(ticket);
    }

    public List<Ticket> findAllTickets() {
        return ticketRepo.findAll();
    }

    public Ticket updateTicket(Ticket ticket) {
        return ticketRepo.save(ticket);
    }

    public Ticket findVendorById(Long id) {
        return ticketRepo.findVendorById(id)
                .orElseThrow(() -> new UserNotFoundException("User by id " + id + " was not found"));
    }

    public void deleteTicketById(Long id) {
        ticketRepo.deleteTicketById(id);
    }
}
