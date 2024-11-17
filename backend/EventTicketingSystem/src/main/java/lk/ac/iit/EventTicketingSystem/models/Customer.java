package lk.ac.iit.EventTicketingSystem.models;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;
@Entity
public class Customer implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long customerId;
    private String firstName;
    private String email;
    private String phone;

    public Customer() {
    }

    public Customer(String phone, String email, String firstName) {
        this.phone = phone;
        this.email = email;
        this.firstName = firstName;
    }

    public Customer(Long customerId, String firstName, String email, String phone) {
        this.customerId = customerId;
        this.firstName = firstName;
        this.email = email;
        this.phone = phone;
    }

    //getters n setters

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
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

    @Override
    public String toString() {
        return "Customer{" +
                "customerId=" + customerId +
                ", firstName='" + firstName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
