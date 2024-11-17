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
    @Column(nullable = false, updatable = false)
    private String ticketCode;

    public Ticket() {
    }

    public Ticket(String ticketCode, Date eventDate, String eventName) {
        this.ticketCode = ticketCode;
        this.eventDate = eventDate;
        this.eventName = eventName;
    }

    public Ticket(Long ticketId, String eventName, Date eventDate, String ticketCode) {
        this.ticketId = ticketId;
        this.eventName = eventName;
        this.eventDate = eventDate;
        this.ticketCode = ticketCode;
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

    public String getTicketCode() {
        return ticketCode;
    }

    public void setTicketCode(String ticketCode) {
        this.ticketCode = ticketCode;
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
