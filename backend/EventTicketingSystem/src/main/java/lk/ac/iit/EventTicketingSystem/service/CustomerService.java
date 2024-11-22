package lk.ac.iit.EventTicketingSystem.service;

import lk.ac.iit.EventTicketingSystem.exception.UserNotFoundException;
import lk.ac.iit.EventTicketingSystem.models.Customer;
import lk.ac.iit.EventTicketingSystem.repository.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
