package lk.ac.iit.EventTicketingSystem.repository;

import lk.ac.iit.EventTicketingSystem.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepo extends JpaRepository<Customer, Long> {
    Optional findVendorById(Long id);

    void deleteCustomerById(Long id);
}
