package lk.ac.iit.EventTicketingSystem;

import lk.ac.iit.EventTicketingSystem.models.Vendor;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;


import java.io.FileOutputStream;
import java.io.IOException;

@SpringBootApplication
@RestController
public class EventTicketingSystemApplication {

	public static void main(String[] args) throws IOException {

		Configuration config = new Configuration();
		Configuration.loadConfiguration(config);




		// SpringApplication.run(EventTicketingSystemApplication.class, args);
	}

}
