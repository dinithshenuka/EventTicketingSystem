// src/main/java/lk/ac/iit/EventTicketingSystem/EventTicketingSystemApplication.java
package lk.ac.iit.EventTicketingSystem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class EventTicketingSystemApplication implements CommandLineRunner {

	@Autowired
	private SystemConfiguration systemConfiguration;

	public static void main(String[] args) {

		SystemConfiguration systemConfiguration = new SystemConfiguration();
		SystemConfiguration.getConfigInputs(systemConfiguration);

        try {
            SystemConfiguration.saveConfiguration(systemConfiguration,"Configuration.json");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        SpringApplication.run(EventTicketingSystemApplication.class, args);
	}

	@Override
	public void run(String... args) {
		SystemConfiguration.getConfigInputs(systemConfiguration);

		try {
			SystemConfiguration.saveConfiguration(systemConfiguration, "Configuration.json");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}