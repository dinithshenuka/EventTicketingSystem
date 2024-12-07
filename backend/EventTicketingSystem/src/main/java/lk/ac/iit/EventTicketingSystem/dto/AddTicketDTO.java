package lk.ac.iit.EventTicketingSystem.dto;

import lk.ac.iit.EventTicketingSystem.models.Event;
import lk.ac.iit.EventTicketingSystem.models.Ticket;

public class AddTicketDTO {
    private Ticket ticket;
    private Event event;
    private int ticketCount;

    public AddTicketDTO() {
    }

    public AddTicketDTO(Ticket ticket, Event event, int ticketCount) {
        this.ticket = ticket;
        this.event = event;
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
