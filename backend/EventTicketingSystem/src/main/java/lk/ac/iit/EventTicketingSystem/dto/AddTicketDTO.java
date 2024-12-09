package lk.ac.iit.EventTicketingSystem.dto;

import lk.ac.iit.EventTicketingSystem.models.Event;
import lk.ac.iit.EventTicketingSystem.models.Ticket;
import lk.ac.iit.EventTicketingSystem.models.Vendor;

public class AddTicketDTO {
    private Ticket ticket;
    private Event event;
    private Vendor vendor;
    private int ticketCount;

    public AddTicketDTO() {
    }

    public AddTicketDTO(Ticket ticket, Event event, Vendor vendor, int ticketCount) {
        this.ticket = ticket;
        this.event = event;
        this.vendor = vendor;
        this.ticketCount = ticketCount;
    }

    // getters and setters


    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Vendor getVendor() {
        return vendor;
    }

    public void setVendor(Vendor vendor) {
        this.vendor = vendor;
    }

    public int getTicketCount() {
        return ticketCount;
    }

    public void setTicketCount(int ticketCount) {
        this.ticketCount = ticketCount;
    }

    @Override
    public String toString() {
        return "AddTicketDTO{" +
                "ticket=" + ticket +
                ", ticketCount=" + ticketCount +
                '}';
    }
}
