package lk.ac.iit.EventTicketingSystem.models;

import java.util.Date;

public class Vendor extends User {
    private int vendorId;
    private String companyName;
    private String companyAddress;

    public Vendor(int vendorId, String companyName, String companyAddress) {
        this.vendorId = vendorId;
        this.companyName = companyName;
        this.companyAddress = companyAddress;
    }

    public Vendor(String firstName, String lastName, String email, String phone, String address, Date dateOfBirth, int vendorId, String companyName, String companyAddress) {
        super(firstName, lastName, email, phone, address, dateOfBirth);
        this.vendorId = vendorId;
        this.companyName = companyName;
        this.companyAddress = companyAddress;
    }

    // getters abd setters
    public int getVendorId() {
        return vendorId;
    }

    public void setVendorId(int vendorId) {
        this.vendorId = vendorId;
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
                ", companyName='" + companyName + '\'' +
                ", companyAddress='" + companyAddress + '\'' +
                '}';
    }
}
