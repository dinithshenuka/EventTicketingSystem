package lk.ac.iit.EventTicketingSystem.models;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;

@Entity(name = "Ticket")
public class Ticket implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long ticketId;
    private double ticketPrice;
    private String ticketType;
    private String ticketStatus;
    @Column(nullable = false, updatable = false)
    private String ticketCode;

    @ManyToOne
    @JoinColumn(name = "vendorId")
    private Vendor vendor;

    @ManyToOne
    @JoinColumn(name = "eventId")
    private Event event;

    public Ticket() {
    }

    public Ticket(double ticketPrice, String ticketType, String ticketStatus) {
        this.ticketPrice = ticketPrice;
        this.ticketType = ticketType;
        this.ticketStatus = ticketStatus;
    }

    public Ticket(Long ticketId, double ticketPrice, String ticketType, String ticketStatus, String ticketCode) {
        this.ticketId = ticketId;
        this.ticketPrice = ticketPrice;
        this.ticketType = ticketType;
        this.ticketStatus = ticketStatus;
        this.ticketCode = ticketCode;
    }

    // getters and setters


    public Long getTicketId() {
        return ticketId;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }

    public double getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(double ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public String getTicketType() {
        return ticketType;
    }

    public void setTicketType(String ticketType) {
        this.ticketType = ticketType;
    }

    public String getTicketStatus() {
        return ticketStatus;
    }

    public void setTicketStatus(String ticketStatus) {
        this.ticketStatus = ticketStatus;
    }

    public String getTicketCode() {
        return ticketCode;
    }

    public void setTicketCode(String ticketCode) {
        this.ticketCode = ticketCode;
    }

    public Vendor getVendor() {
        return vendor;
    }

    public void setVendor(Vendor vendor) {
        this.vendor = vendor;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return Objects.equals(ticketId, ticket.ticketId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ticketId);
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "ticketId=" + ticketId +
                ", ticketPrice=" + ticketPrice +
                ", ticketType='" + ticketType + '\'' +
                ", ticketStatus='" + ticketStatus + '\'' +
                ", ticketCode='" + ticketCode + '\'' +
                '}';
    }
}
