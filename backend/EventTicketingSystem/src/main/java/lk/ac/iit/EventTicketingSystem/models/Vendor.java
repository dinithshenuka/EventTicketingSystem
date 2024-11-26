package lk.ac.iit.EventTicketingSystem.models;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;

@Entity(name = "Vendor")
public class Vendor implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private int vendorId;
    private String firstName;
    private String email;
    @Column(unique = true)
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

    public Vendor(String vendorCode, String companyAddress, String companyName, String phone, String email, String firstName) {
        this.vendorCode = vendorCode;
        this.companyAddress = companyAddress;
        this.companyName = companyName;
        this.phone = phone;
        this.email = email;
        this.firstName = firstName;
    }

    public Vendor(int vendorId, String firstName, String email, String phone, String companyName, String companyAddress, String vendorCode) {
        this.vendorId = vendorId;
        this.firstName = firstName;
        this.email = email;
        this.phone = phone;
        this.companyName = companyName;
        this.companyAddress = companyAddress;
        this.vendorCode = vendorCode;
    }

    //getters setters

    public int getVendorId() {
        return vendorId;
    }

    public void setVendorId(int vendorId) {
        this.vendorId = vendorId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
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
                ", firstName='" + firstName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", companyName='" + companyName + '\'' +
                ", companyAddress='" + companyAddress + '\'' +
                '}';
    }
}


