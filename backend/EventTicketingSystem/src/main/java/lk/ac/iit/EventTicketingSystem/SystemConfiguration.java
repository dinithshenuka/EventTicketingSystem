package lk.ac.iit.EventTicketingSystem;

import com.google.gson.Gson;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.InputMismatchException;
import java.util.Scanner;

@Component
public class SystemConfiguration implements Serializable {
    private int totalTickets;
    private int ticketReleaseRate;
    private int customerRetrievalRate;
    private int maxTicketCapacity;

    public SystemConfiguration() {
    }

    public SystemConfiguration(int totalTickets, int ticketReleaseRate, int customerRetrievalRate, int maxTicketCapacity) {
        this.totalTickets = totalTickets;
        this.ticketReleaseRate = ticketReleaseRate;
        this.customerRetrievalRate = customerRetrievalRate;
        this.maxTicketCapacity = maxTicketCapacity;
    }

    // method to get input and validate
    public static void getConfigInputs(SystemConfiguration systemConfiguration) {
        Scanner input = new Scanner(System.in);
        System.out.println("\n=== Ticket System Configuration ===");

        // total number of tickets
        while (true) {
            try {
                System.out.print("Enter Total Tickets: ");
                int newTotalTickets = input.nextInt();
                systemConfiguration.setTotalTickets(newTotalTickets);
                break;
            } catch (InputMismatchException e) {
                System.out.println("Error: Please enter a integer value greater than 0");
                input.next();
            }
        }

        // ticket release rate
        while (true) {
            try {
                System.out.print("Enter Ticket Release Rate: ");
                int newTicketReleaseRate = input.nextInt();
                systemConfiguration.setTicketReleaseRate(newTicketReleaseRate);
                break;
            } catch (InputMismatchException e) {
                System.out.println("Error: Please enter a integer value greater than 0");
                input.next();
            }
        }

        // customer retrieval rate
        while (true) {
            try {
                System.out.print("Enter Customer Retrieval Rate: ");
                int newCustomerRetrievalRate = input.nextInt();
                systemConfiguration.setCustomerRetrievalRate(newCustomerRetrievalRate);
                break;
            } catch (InputMismatchException e) {
                System.out.println("Error: Please enter a integer value greater than 0");
                input.next();
            }
        }

        // Max Ticket Capacity
        while (true) {
            try {
                System.out.print("Enter Max Ticket Capacity: ");
                int newMaxTicketCapacity = input.nextInt();
                systemConfiguration.setMaxTicketCapacity(newMaxTicketCapacity);
                break;
            } catch (InputMismatchException e) {
                System.out.println("Error: Please enter a integer value greater than 0");
                input.next();
            }
        }
    }

    // save object
    public static void saveConfiguration(SystemConfiguration config, String filePath) throws IOException {
        Gson gson = new Gson();
        String json = gson.toJson(config);

        // Write JSON string to file
        FileWriter writer = new FileWriter(filePath);
        writer.write(json);
        writer.close();

        System.out.println("Configuration saved successfully to: " + filePath);
    }




    //getters and setters

    public int getTotalTickets() {
        return totalTickets;
    }

    public void setTotalTickets(int totalTickets) {
        this.totalTickets = totalTickets;
    }

    public int getTicketReleaseRate() {
        return ticketReleaseRate;
    }

    public void setTicketReleaseRate(int ticketReleaseRate) {
        this.ticketReleaseRate = ticketReleaseRate;
    }

    public int getCustomerRetrievalRate() {
        return customerRetrievalRate;
    }

    public void setCustomerRetrievalRate(int customerRetrievalRate) {
        this.customerRetrievalRate = customerRetrievalRate;
    }

    public int getMaxTicketCapacity() {
        return maxTicketCapacity;
    }

    public void setMaxTicketCapacity(int maxTicketCapacity) {
        this.maxTicketCapacity = maxTicketCapacity;
    }


    @Override
    public String toString() {
        return "Configuration{" +
                "totalTickets=" + totalTickets +
                ", ticketReleaseRate=" + ticketReleaseRate +
                ", customerRetrievalRate=" + customerRetrievalRate +
                ", maxTicketCapacity=" + maxTicketCapacity +
                '}';
    }
}