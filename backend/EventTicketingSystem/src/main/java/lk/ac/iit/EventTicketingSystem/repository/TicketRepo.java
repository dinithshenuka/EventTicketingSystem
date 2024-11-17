package lk.ac.iit.EventTicketingSystem.repository;

import lk.ac.iit.EventTicketingSystem.models.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TicketRepo extends JpaRepository<Ticket, Integer> {

    void deleteTicketById(Long id);

    Optional<Ticket> findTicketById(Long id);
}
