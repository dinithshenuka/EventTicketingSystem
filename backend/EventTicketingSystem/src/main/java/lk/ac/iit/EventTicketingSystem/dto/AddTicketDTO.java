package lk.ac.iit.EventTicketingSystem.dto;

import lk.ac.iit.EventTicketingSystem.models.Ticket;

public class AddTicketDTO {
    private Ticket ticket;
    private int ticketCount;

    public AddTicketDTO() {
    }

    public AddTicketDTO(Ticket ticket, int ticketCount) {
        this.ticket = ticket;
        this.ticketCount = ticketCount;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
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
