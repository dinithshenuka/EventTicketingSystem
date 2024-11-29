package lk.ac.iit.EventTicketingSystem.service;

import lk.ac.iit.EventTicketingSystem.SystemConfiguration;
import lk.ac.iit.EventTicketingSystem.models.Ticket;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class TicketPool {
    private final List<Ticket> ticketPoolList = new ArrayList<>();
    private final SystemConfiguration systemConfiguration;
    private final ReentrantLock lock = new ReentrantLock();
    private final Condition notFull = lock.newCondition();
    private final Condition notEmpty = lock.newCondition();

    public TicketPool(SystemConfiguration systemConfiguration) {
        this.systemConfiguration = systemConfiguration;
    }

    // add ticket to the pool (Vendor add tickets)
    public void addToTicketPool(Ticket ticket) {
        lock.lock();
        try {
            while (ticketPoolList.size() == systemConfiguration.getMaxTicketPoolCapacity()) {
                notFull.await();
            }
            ticketPoolList.add(ticket);
            notEmpty.signalAll();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            lock.unlock();
        }
    }

    // remove tickets from pool (Customer buy tickets)
    public void removeFromTicketPool(Ticket ticket) {
        lock.lock();
        try {
            while (ticketPoolList.isEmpty()) {
                notEmpty.await();
            }
            ticketPoolList.remove(ticket);
            notFull.signalAll();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            lock.unlock();
        }
    }

    // get all tickets in the pool
    public List<Ticket> getAllTicketsInPool() {
        lock.lock();
        try {
            return new ArrayList<>(ticketPoolList);
        } finally {
            lock.unlock();
        }
    }

    // get ticket count in the pool
    public int getTicketCountInPool() {
        lock.lock();
        try {
            return ticketPoolList.size();
        } finally {
            lock.unlock();
        }
    }
}