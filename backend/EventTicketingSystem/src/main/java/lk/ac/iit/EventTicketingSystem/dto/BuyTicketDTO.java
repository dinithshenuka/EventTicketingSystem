package lk.ac.iit.EventTicketingSystem.dto;

import lk.ac.iit.EventTicketingSystem.models.Customer;
import lk.ac.iit.EventTicketingSystem.models.Ticket;

public class BuyTicketDTO {
    private Customer customer;
    private int ticketCount;


    public BuyTicketDTO() {
    }

    public BuyTicketDTO(Ticket ticket, Customer customer) {
        this.customer = customer;
    }


    // getters and setters

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public int getTicketCount() {
        return ticketCount;
    }

    public void setTicketCount(int ticketCount) {
        this.ticketCount = ticketCount;
    }
}
