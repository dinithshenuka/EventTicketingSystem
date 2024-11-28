package lk.ac.iit.EventTicketingSystem.dto;

import lk.ac.iit.EventTicketingSystem.models.Customer;

public class BuyTicketDTO {
    private Customer customer;

    public BuyTicketDTO() {
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
