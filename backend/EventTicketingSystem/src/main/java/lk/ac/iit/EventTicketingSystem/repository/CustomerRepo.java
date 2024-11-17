package lk.ac.iit.EventTicketingSystem.repository;

import lk.ac.iit.EventTicketingSystem.models.Customer;
import lk.ac.iit.EventTicketingSystem.models.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepo extends JpaRepository<Customer, Long> {

    void deleteCustomerById(Long id);

    Optional<Customer> findCustomerById(Long id);
}
