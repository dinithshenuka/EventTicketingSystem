package lk.ac.iit.EventTicketingSystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class EventTicketingSystemApplication {

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
}
