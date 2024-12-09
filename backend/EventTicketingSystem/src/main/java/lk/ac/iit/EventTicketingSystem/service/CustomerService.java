package lk.ac.iit.EventTicketingSystem.service;

import lk.ac.iit.EventTicketingSystem.exception.UserNotFoundException;
import lk.ac.iit.EventTicketingSystem.models.Customer;
import lk.ac.iit.EventTicketingSystem.repository.CustomerRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CustomerService {
    private final CustomerRepo customerRepo;
    @Autowired
    public CustomerService(CustomerRepo customerRepo) {
        this.customerRepo = customerRepo;
    }

    Logger logger = LoggerFactory.getLogger(CustomerService.class);

    public Customer addCustomer(Customer customer) {
        customer.setCustomerCode(UUID.randomUUID().toString());
        return customerRepo.save(customer);
    }

    public Customer findOrAddCustomer(String firstName, String email, String phone) {
        Optional<Customer> existingCustomer = customerRepo.findByFirstNameAndEmailAndPhone(firstName, email, phone);

        if (existingCustomer.isPresent()) {
            // Log that the customer was found
            logger.info("Customer found: {}", existingCustomer.get());
            return existingCustomer.get();
        } else {
            // Create a new customer if none found
            Customer newCustomer = new Customer();
            newCustomer.setFirstName(firstName);
            newCustomer.setEmail(email);
            newCustomer.setPhone(phone);
            // Log that a new customer was created
            logger.info("No existing customer found. Creating new customer: {}", newCustomer);
            return addCustomer(newCustomer);
        }
    }

    public List<Customer> findAllCustomers() {
        return customerRepo.findAll();
    }

    public Customer updateCustomer(Customer customer) {
        return customerRepo.save(customer);
    }

    public Customer findCustomerById(Long customerId) {
        return customerRepo.findById(customerId)
                .orElseThrow(() -> new UserNotFoundException("User by id " + customerId + " was not found"));
    }

    public void deleteCustomer(Long customerId) {
        customerRepo.deleteById(customerId);
    }

}
