package lk.ac.iit.EventTicketingSystem.models;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
public class Vendor implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private int vendorId;
    private String firstName;
    private String email;
    private String phone;
    private String companyName;
    private String companyAddress;

    public Vendor() {
    }

    public Vendor(String companyAddress, String companyName, String phone, String email, String firstName) {
        this.companyAddress = companyAddress;
        this.companyName = companyName;
        this.phone = phone;
        this.email = email;
        this.firstName = firstName;
    }

    public Vendor(int vendorId, String firstName, String email, String phone, String companyName, String companyAddress) {
        this.vendorId = vendorId;
        this.firstName = firstName;
        this.email = email;
        this.phone = phone;
        this.companyName = companyName;
        this.companyAddress = companyAddress;
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

