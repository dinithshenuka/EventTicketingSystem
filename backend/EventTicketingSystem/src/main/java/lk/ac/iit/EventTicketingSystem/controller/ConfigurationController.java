package lk.ac.iit.EventTicketingSystem.controller;

import lk.ac.iit.EventTicketingSystem.SystemConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/configuration")
public class ConfigurationController {

    private final SystemConfiguration systemConfiguration;

    @Autowired
    public ConfigurationController(SystemConfiguration systemConfiguration) {
        this.systemConfiguration = systemConfiguration;
    }

    // update the system configuration
    @PutMapping("/update")
    public ResponseEntity<SystemConfiguration> updateConfiguration(@RequestBody SystemConfiguration systemConfiguration) {
        SystemConfiguration updatedConfiguration = systemConfiguration.updateConfiguration(systemConfiguration);
        return new ResponseEntity<>(updatedConfiguration, HttpStatus.OK);
    }

    // start and stop
    @PostMapping("/start")
    public ResponseEntity<Void> start() {
        systemConfiguration.setRunning(true);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/stop")
    public ResponseEntity<Void> stop() {
        systemConfiguration.setRunning(false);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
