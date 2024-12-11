package lk.ac.iit.EventTicketingSystem.controller;

import lk.ac.iit.EventTicketingSystem.SystemConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/configuration")
public class ConfigurationController {

    @Autowired
    public ConfigurationController(SystemConfiguration systemConfiguration) {
    }

    // update the system configuration
    @PutMapping("/update")
    public ResponseEntity<SystemConfiguration> updateConfiguration(@RequestBody SystemConfiguration systemConfiguration) {
        SystemConfiguration updatedConfiguration = systemConfiguration.updateConfiguration(systemConfiguration);
        return new ResponseEntity<>(updatedConfiguration, HttpStatus.OK);
    }

}
