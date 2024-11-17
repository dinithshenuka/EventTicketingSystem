package lk.ac.iit.EventTicketingSystem.service;

import lk.ac.iit.EventTicketingSystem.exception.UserNotFoundException;
import lk.ac.iit.EventTicketingSystem.models.Vendor;
import lk.ac.iit.EventTicketingSystem.repository.VendorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public List<Vendor> findAllVendors() {
        return vendorRepo.findAll();
    }

    public Vendor updateVendor(Vendor vendor) {
        return vendorRepo.save(vendor);
    }

    public Vendor findVendorById(Long id) {
        return vendorRepo.findVendorById(id)
                .orElseThrow(() -> new UserNotFoundException("User by id " + id + " was not found"));
    }

    public void deleteVendor(Long id){
        vendorRepo.deleteVendorById(id);
    }

}
