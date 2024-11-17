package lk.ac.iit.EventTicketingSystem.repository;

import lk.ac.iit.EventTicketingSystem.models.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VendorRepo extends JpaRepository<Vendor, Long> {

    Optional findVendorById(Long id);

    void deleteVendorById(Long id);
}
