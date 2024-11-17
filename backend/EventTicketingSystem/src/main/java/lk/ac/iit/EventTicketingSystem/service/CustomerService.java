package lk.ac.iit.EventTicketingSystem.service;

import lk.ac.iit.EventTicketingSystem.models.Customer;
import lk.ac.iit.EventTicketingSystem.repository.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CustomerService {
    private final CustomerRepo customerRepo;
    @Autowired
    public CustomerService(CustomerRepo customerRepo) {
        this.customerRepo = customerRepo;
    }

    public Customer addCustomer(Customer customer) {
        customer.setCustomerCode(UUID.randomUUID().toString());
        return customerRepo.save(customer);
    }
}
