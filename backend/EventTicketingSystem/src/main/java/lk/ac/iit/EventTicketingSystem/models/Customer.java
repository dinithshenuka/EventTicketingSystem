package lk.ac.iit.EventTicketingSystem.models;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;

@Entity(name = "Customer")
public class Customer implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long customerId;
    private String firstName;
    private String email;
    private String phone;
    @Column(nullable = false, updatable = false)
    private String customerCode;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Ticket> tickets;

    public Customer() {
    }

    public Customer(String customerCode, String phone, String email, String firstName) {
        this.customerCode = customerCode;
        this.phone = phone;
        this.email = email;
        this.firstName = firstName;
    }

    public Customer(Long customerId, String firstName, String email, String phone, String customerCode) {
        this.customerId = customerId;
        this.firstName = firstName;
        this.email = email;
        this.phone = phone;
        this.customerCode = customerCode;
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

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
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
