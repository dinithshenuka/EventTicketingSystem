package lk.ac.iit.EventTicketingSystem.repository;

import lk.ac.iit.EventTicketingSystem.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepo extends JpaRepository<Customer, Long> {
}
