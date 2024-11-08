package lk.ac.iit.EventTicketingSystem;

import lk.ac.iit.EventTicketingSystem.configuration.Configuration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

import java.util.Scanner;

@SpringBootApplication
@RestController
public class EventTicketingSystemApplication {

	public static void main(String[] args) {

		Scanner input = new Scanner(System.in);

		System.out.println("Welcome to the Real-Time Event Ticketing System!");

		System.out.print("Enter total tickets: ");
		int totalTickets = input.nextInt();
		System.out.print("Enter ticket release rate: ");
		int ticketReleaseRate = input.nextInt();
		System.out.print("Enter customer retrieval rate: ");
		int customerRetrievalRate = input.nextInt();
		System.out.print("Enter max ticket capacity: ");
		int maxTicketCapacity = input.nextInt();

		Configuration config = new Configuration(totalTickets,ticketReleaseRate,customerRetrievalRate,maxTicketCapacity);

		System.out.println(config);

		SpringApplication.run(EventTicketingSystemApplication.class, args);
	}

}
