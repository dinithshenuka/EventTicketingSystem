package lk.ac.iit.EventTicketingSystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException;

@SpringBootApplication
public class EventTicketingSystemApplication {

	public static void main(String[] args) throws IOException {

		SpringApplication.run(EventTicketingSystemApplication.class, args);
	}

}
