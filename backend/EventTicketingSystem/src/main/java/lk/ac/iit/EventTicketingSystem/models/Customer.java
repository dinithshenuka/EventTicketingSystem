package lk.ac.iit.EventTicketingSystem.models;

import java.util.Date;

public class Customer extends User {
    private int customerId;

    public Customer(int customerId) {
        this.customerId = customerId;
    }

    public Customer(String firstName, String lastName, String email, String phone, String address, Date dateOfBirth, int customerId) {
        super(firstName, lastName, email, phone, address, dateOfBirth);
        this.customerId = customerId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customerId=" + customerId +
                '}';
    }
}
