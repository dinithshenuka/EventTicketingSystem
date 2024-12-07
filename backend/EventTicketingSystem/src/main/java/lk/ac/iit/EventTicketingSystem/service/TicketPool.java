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
//
//    public List<Ticket> getAllTicketsForEvent(Event event) {
//        BlockingQueue<Ticket> ticketQueue = ticketPoolMap.get(event);
//        if (ticketQueue == null) {
//            return List.of();
//        }
//        return ticketQueue.stream().collect(Collectors.toList());
//    }
//
//    public int getTicketCountForEvent(Event event) {
//        BlockingQueue<Ticket> ticketQueue = ticketPoolMap.get(event);
//        return (ticketQueue != null) ? ticketQueue.size() : 0;
//    }
//
//    public void removeEvent(Event event) {
//        ticketPoolMap.remove(event);
//    }

    public int getTicketCountInPool() {
        return ticketPoolMap.values().stream()
                .mapToInt(BlockingQueue::size)
                .sum();
    }

//    public List<Event> getAllEvents() {
//        return ticketPoolMap.keySet().stream().collect(Collectors.toList());
//    }
}