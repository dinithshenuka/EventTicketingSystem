package lk.ac.iit.EventTicketingSystem.controller;

import lk.ac.iit.EventTicketingSystem.models.Vendor;
import lk.ac.iit.EventTicketingSystem.service.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/vendor")
public class VendorController {
    private final VendorService vendorService;

    @Autowired
    public VendorController(VendorService vendorService) {
        this.vendorService = vendorService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Vendor>> getAllVendors() {
     List<Vendor> vendors = vendorService.findAllVendors();
     return new ResponseEntity<>(vendors, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Vendor> getVendorById(@PathVariable("id") Long id) {
        Vendor vendor = vendorService.findVendorById(id);
        return new ResponseEntity<>(vendor, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Vendor> addVendor(@RequestBody Vendor vendor) {
        Vendor newVendor = vendorService.addVendor(vendor);
        return new ResponseEntity<>(newVendor, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<Vendor> updateVendor(@RequestBody Vendor vendor) {
        Vendor updatedVendor = vendorService.updateVendor(vendor);
        return new ResponseEntity<>(updatedVendor, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteVendor(@PathVariable("id") Long id) {
        vendorService.deleteVendor(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
