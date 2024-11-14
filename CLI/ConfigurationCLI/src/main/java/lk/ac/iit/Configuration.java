package lk.ac.iit;

import com.google.gson.Gson;
import java.io.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Configuration implements Serializable {
    private int totalTickets;
    private int ticketReleaseRate;
    private int customerRetrievalRate;
    private int maxTicketCapacity;

    // Default configuration
    public Configuration() {
    }

    public Configuration(int totalTickets, int ticketReleaseRate, int customerRetrievalRate, int maxTicketCapacity) {
        this.totalTickets = totalTickets;
        this.ticketReleaseRate = ticketReleaseRate;
        this.customerRetrievalRate = customerRetrievalRate;
        this.maxTicketCapacity = maxTicketCapacity;
    }

    // method to get input and validate
    public static void getConfigInputs(Configuration config) {
        Scanner input = new Scanner(System.in);
        System.out.println("\n=== Ticket System Configuration ===");

        // total number of tickets
        while (true) {
            try {
                System.out.print("Enter Total Tickets: ");
                int newTotalTickets = input.nextInt();
                config.setTotalTickets(newTotalTickets);
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
                config.setTicketReleaseRate(newTicketReleaseRate);
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
                config.setCustomerRetrievalRate(newCustomerRetrievalRate);
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
                config.setMaxTicketCapacity(newMaxTicketCapacity);
                break;
            } catch (InputMismatchException e) {
                System.out.println("Error: Please enter a integer value greater than 0");
                input.next();
            }
        }
    }

    // save object
    public static void saveConfiguration(Configuration config, String filePath) throws IOException {
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
