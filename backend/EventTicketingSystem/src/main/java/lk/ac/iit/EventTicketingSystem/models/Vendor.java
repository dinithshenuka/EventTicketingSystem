package lk.ac.iit.EventTicketingSystem.models;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;

@Entity(name = "Vendor")
public class Vendor implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long vendorId;
    private String vendorName;
    private String email;
    private String phone;
    private String companyName;
    private String companyAddress;
    @Column(nullable = false, updatable = false)
    private String vendorCode;

    @ManyToMany
    @JoinTable(
            name = "vendor_event",
            joinColumns = @JoinColumn(name = "vendorId"),
            inverseJoinColumns = @JoinColumn(name = "eventId")
    )
    private List<Event> events;

    @OneToMany(mappedBy = "vendor", cascade = CascadeType.ALL)
    private List<Ticket> tickets;

    public Vendor() {
    }

    public Vendor(String vendorName, String email, String phone, String companyName, String companyAddress, List<Event> events, List<Ticket> tickets) {
        this.vendorName = vendorName;
        this.email = email;
        this.phone = phone;
        this.companyName = companyName;
        this.companyAddress = companyAddress;
        this.events = events;
        this.tickets = tickets;
    }

    public Vendor(Long vendorId, String vendorName, String email, String phone, String companyName, String companyAddress, String vendorCode, List<Event> events, List<Ticket> tickets) {
        this.vendorId = vendorId;
        this.vendorName = vendorName;
        this.email = email;
        this.phone = phone;
        this.companyName = companyName;
        this.companyAddress = companyAddress;
        this.vendorCode = vendorCode;
        this.events = events;
        this.tickets = tickets;
    }

    //getters setters

    public Long getVendorId() {
        return vendorId;
    }

    public void setVendorId(Long vendorId) {
        this.vendorId = vendorId;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public String getVendorCode() {
        return vendorCode;
    }

    public void setVendorCode(String vendorCode) {
        this.vendorCode = vendorCode;
    }

    @Override
    public String toString() {
        return "Vendor{" +
                "vendorId=" + vendorId +
                ", vendorName='" + vendorName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", companyName='" + companyName + '\'' +
                ", companyAddress='" + companyAddress + '\'' +
                ", vendorCode='" + vendorCode + '\'' +
                ", events=" + events +
                ", tickets=" + tickets +
                '}';
    }
}


