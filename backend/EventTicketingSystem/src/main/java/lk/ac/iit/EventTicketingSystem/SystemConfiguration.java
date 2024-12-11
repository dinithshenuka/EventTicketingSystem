// src/main/java/lk/ac/iit/EventTicketingSystem/SystemConfiguration.java
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
    private boolean running;

    public SystemConfiguration() {
        this.running = true; // Default to running
    }

    // Method to get input and validate
    public static void getConfigInputs(SystemConfiguration systemConfiguration) {
        Scanner input = new Scanner(System.in);
        System.out.println("\n=== Ticket System Configuration ===");

        // Total number of tickets
        while (true) {
            try {
                System.out.print("Enter Total Tickets: ");
                int newTotalTickets = input.nextInt();
                systemConfiguration.setTotalTickets(newTotalTickets);
                break;
            } catch (InputMismatchException e) {
                System.out.println("Error: Please enter an integer value greater than 0");
                input.next();
            }
        }

        // Ticket release rate
        while (true) {
            try {
                System.out.print("Enter Ticket Release Rate (1-5): ");
                int newTicketReleaseRate = input.nextInt();
                if (newTicketReleaseRate >= 1 && newTicketReleaseRate <= 5) {
                    systemConfiguration.setTicketReleaseRate(newTicketReleaseRate);
                    break;
                } else {
                    System.out.println("Error: Please enter an integer value between 1 and 5");
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Please enter an integer value between 1 and 5");
                input.next();
            }
        }

        // Customer retrieval rate
        while (true) {
            try {
                System.out.print("Enter Customer Retrieval Rate (1-5): ");
                int newCustomerRetrievalRate = input.nextInt();
                if (newCustomerRetrievalRate >= 1 && newCustomerRetrievalRate <= 5) {
                    systemConfiguration.setCustomerRetrievalRate(newCustomerRetrievalRate);
                    break;
                } else {
                    System.out.println("Error: Please enter an integer value between 1 and 5");
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Please enter an integer value between 1 and 5");
                input.next();
            }
        }

        // Max Ticket Capacity
        while (true) {
            try {
                System.out.print("Enter Max Ticket Capacity: ");
                int newMaxTicketCapacity = input.nextInt();
                if (newMaxTicketCapacity > 0 && newMaxTicketCapacity <= systemConfiguration.getTotalTickets()) {
                    systemConfiguration.setMaxTicketCapacity(newMaxTicketCapacity);
                    break;
                } else {
                    System.out.println("Error: Max Ticket Capacity must be greater than 0 and less than Total Tickets");
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Please enter an integer value greater than 0");
                input.next();
            }
        }
    }

    // Save object
    public static void saveConfiguration(SystemConfiguration config, String filePath) throws IOException {
        Gson gson = new Gson();
        String json = gson.toJson(config);

        // Write JSON string to file
        FileWriter writer = new FileWriter(filePath);
        writer.write(json);
        writer.close();

        System.out.println("Configuration saved successfully to: " + filePath);
    }

    // Update configuration
    public SystemConfiguration updateConfiguration(SystemConfiguration systemConfiguration) {
        this.totalTickets = systemConfiguration.getTotalTickets();
        this.ticketReleaseRate = systemConfiguration.getTicketReleaseRate();
        this.customerRetrievalRate = systemConfiguration.getCustomerRetrievalRate();
        this.maxTicketCapacity = systemConfiguration.getMaxTicketCapacity();
        return this;

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

    public void setMaxTicketCapacity(int maxTicketCapacity) {
        this.maxTicketCapacity = maxTicketCapacity;
    }

    //  running flag
    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;


    @Override
    public String toString() {
        return "Configuration{" +
                "totalTickets=" + totalTickets +
                ", ticketReleaseRate=" + ticketReleaseRate +
                ", customerRetrievalRate=" + customerRetrievalRate +
                ", maxTicketCapacity=" + maxTicketCapacity +
                ", running=" + running +
                '}';
    }
}