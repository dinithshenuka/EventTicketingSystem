package lk.ac.iit.EventTicketingSystem.service;

import lk.ac.iit.EventTicketingSystem.exception.UserNotFoundException;
import lk.ac.iit.EventTicketingSystem.models.Vendor;
import lk.ac.iit.EventTicketingSystem.repository.VendorRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class VendorService {
    private final VendorRepo vendorRepo;
    private final Logger logger = LoggerFactory.getLogger(VendorService.class);

    @Autowired
    public VendorService(VendorRepo vendorRepo) {
        this.vendorRepo = vendorRepo;
    }

    public Vendor addVendor(Vendor vendor) {
        vendor.setVendorCode(UUID.randomUUID().toString());
        logger.info("Vendor added: {}", vendor.getVendorName());
        return vendorRepo.save(vendor);
    }

    public List<Vendor> findAllVendors() {
        return vendorRepo.findAll();
    }

    public Vendor updateVendor(Vendor vendor) {
        return vendorRepo.save(vendor);
    }

    public Vendor findVendorById(Long vendorId) {
        return vendorRepo.findById(vendorId)
                .orElseThrow(() -> new UserNotFoundException("User by id " + vendorId + " was not found"));
    }

    public void deleteVendor(Long vendorId) {
        vendorRepo.deleteById(vendorId);
    }
}