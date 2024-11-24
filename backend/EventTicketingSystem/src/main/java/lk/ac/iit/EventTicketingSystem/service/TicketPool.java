package lk.ac.iit.EventTicketingSystem.service;

import lk.ac.iit.EventTicketingSystem.models.Ticket;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class TicketPool {
    private final List<Ticket> ticketList = Collections.synchronizedList(new ArrayList<>());


    public void addToTicketPool(Ticket ticket) {
        ticketList.add(ticket); // Adds a ticket to the pool
    }

    public List<Ticket> getAllTicketsInPool() {
        synchronized (ticketList) {
            return new ArrayList<>(ticketList);
        }
    }

    public int getTicketCountInPool() {
        synchronized (ticketList) {
            return ticketList.size(); // Thread-safe access to the size of the list
        }
    }



    @Override
    public String toString() {
        return "TicketPool{" +
                "ticketList=" + ticketList +
                '}';
    }
}
