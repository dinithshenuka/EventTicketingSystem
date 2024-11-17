package lk.ac.iit.EventTicketingSystem.models;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;

@Entity
public class Ticket implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long ticketId;
    private String eventName;
    private Date eventDate;

    public Ticket() {
    }

    public Ticket(Date eventDate, String eventName) {
        this.eventDate = eventDate;
        this.eventName = eventName;
    }

    public Ticket(Long ticketId, String eventName, Date eventDate) {
        this.ticketId = ticketId;
        this.eventName = eventName;
        this.eventDate = eventDate;
    }

    // getters and setters

    public Long getTicketId() {
        return ticketId;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "ticketId=" + ticketId +
                ", eventName='" + eventName + '\'' +
                ", eventDate=" + eventDate +
                '}';
    }
}
