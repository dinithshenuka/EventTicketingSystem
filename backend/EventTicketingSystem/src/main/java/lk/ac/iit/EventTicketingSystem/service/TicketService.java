package lk.ac.iit.EventTicketingSystem.service;

import lk.ac.iit.EventTicketingSystem.models.Ticket;
import lk.ac.iit.EventTicketingSystem.repository.TicketRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
