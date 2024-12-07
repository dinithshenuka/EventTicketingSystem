package lk.ac.iit.EventTicketingSystem.service;

import lk.ac.iit.EventTicketingSystem.models.Event;
import lk.ac.iit.EventTicketingSystem.models.Ticket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Collectors;

@Service
public class TicketPool {
    private final Map<Long, BlockingQueue<Ticket>> ticketPoolMap = new ConcurrentHashMap<>();

    // logger
    Logger logger = LoggerFactory.getLogger(TicketPool.class);

    public void addToTicketPool(Long eventId, Ticket ticket) {
        ticketPoolMap.computeIfAbsent(eventId, e -> new LinkedBlockingQueue<>()).add(ticket);
        logger.info("Ticket added to the pool: {}", ticket);
    }

    public Ticket removeFromTicketPool(Long eventId) throws InterruptedException {
        BlockingQueue<Ticket> ticketQueue = ticketPoolMap.get(eventId);
        if (ticketQueue == null) {
            throw new IllegalArgumentException("No tickets available for the specified event.");
        }
        logger.info("Ticket removed from the pool: {}", ticketQueue.peek());
        return ticketQueue.take();
    }

    // get all tickets in the pool
    public List<Ticket> getAllTicketsInPool() {
        return ticketPoolMap.values().stream()
                .flatMap(BlockingQueue::stream)
                .collect(Collectors.toList());
    }

    // all for a specific event
    public List<Ticket> getAllTicketsForEvent(Long eventId) {
        BlockingQueue<Ticket> ticketQueue = ticketPoolMap.get(eventId);
        if (ticketQueue == null) {
            return List.of();
        }
        return ticketQueue.stream().collect(Collectors.toList());
    }

    // get ticket count for a specific event
    public int getTicketCountForEvent(Long eventId) {
        BlockingQueue<Ticket> ticketQueue = ticketPoolMap.get(eventId);
        return (ticketQueue != null) ? ticketQueue.size() : 0;
    }

    // remove event (call from event controller)
    public void removeEvent(Long eventId) {
        ticketPoolMap.remove(eventId);
    }

    // get ticket count in the pool
    public int getTicketCountInPool() {
        return ticketPoolMap.values().stream()
                .mapToInt(BlockingQueue::size)
                .sum();
    }
}