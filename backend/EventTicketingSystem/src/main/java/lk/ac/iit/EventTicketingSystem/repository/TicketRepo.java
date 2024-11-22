package lk.ac.iit.EventTicketingSystem.repository;

import lk.ac.iit.EventTicketingSystem.models.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepo extends JpaRepository<Ticket, Long> {
}
