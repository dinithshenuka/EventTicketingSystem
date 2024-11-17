package lk.ac.iit.EventTicketingSystem.service;

import lk.ac.iit.EventTicketingSystem.models.Vendor;
import lk.ac.iit.EventTicketingSystem.repository.VendorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class VendorService {
    private final VendorRepo vendorRepo;

    @Autowired
    public VendorService(VendorRepo vendorRepo) {
        this.vendorRepo = vendorRepo;
    }

    public Vendor addVendor(Vendor vendor) {
        vendor.setVendorCode(UUID.randomUUID().toString());
        return vendorRepo.save(vendor);
    }

}
