package lk.ac.iit.EventTicketingSystem.controller;

import lk.ac.iit.EventTicketingSystem.dto.AddTicketDTO;
import lk.ac.iit.EventTicketingSystem.dto.BuyTicketDTO;
import lk.ac.iit.EventTicketingSystem.models.Ticket;
import lk.ac.iit.EventTicketingSystem.service.TicketPool;
import lk.ac.iit.EventTicketingSystem.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/ticket")
public class TicketController {
    private final TicketService ticketService;
    private final TicketPool ticketPool;

    @Autowired
    public TicketController(TicketService ticketService, TicketPool ticketPool) {
        this.ticketService = ticketService;
        this.ticketPool = ticketPool;
    }

    // vendor add tickets
    @PostMapping("/add")
    public CompletableFuture<ResponseEntity<List<Ticket>>> addTicket(@RequestBody AddTicketDTO addTicketDTO) {
        return ticketService.addTicket(addTicketDTO)
                .thenApply(newTickets -> new ResponseEntity<>(newTickets, HttpStatus.CREATED));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Ticket>> getAllTickets() {
        List<Ticket> tickets = ticketService.findAllTickets();
        return new ResponseEntity<>(tickets, HttpStatus.OK);
    }

    // get all tickets in pool
    @GetMapping("/all/pool")
    public ResponseEntity<List<Ticket>> getAllTicketsInPool() {
        List<Ticket> tickets = ticketPool.getAllTicketsInPool();
        return new ResponseEntity<>(tickets, HttpStatus.OK);
    }

    // ticket count from the pool
    @GetMapping("/count")
    public ResponseEntity<Integer> getTicketCount() {
        int ticketCount = ticketPool.getTicketCountInPool();
        return new ResponseEntity<>(ticketCount, HttpStatus.OK);
    }

    // get ticket count for a specific event
    @GetMapping("/count/{eventId}")
    public ResponseEntity<Integer> getTicketCountForEvent(@PathVariable Long eventId) {
        int ticketCount = ticketPool.getTicketCountForEvent(eventId);
        return new ResponseEntity<>(ticketCount, HttpStatus.OK);
    }

    // getAllTicketsForEvent in ticket pool
    @GetMapping("/all/{eventId}")
    public ResponseEntity<List<Ticket>> getAllTicketsForEvent(@PathVariable Long eventId) {
        List<Ticket> tickets = ticketPool.getAllTicketsForEvent(eventId);
        return new ResponseEntity<>(tickets, HttpStatus.OK);
    }

    @GetMapping("/find/{ticketId}")
    public ResponseEntity<Ticket> getTicketById(@PathVariable Long ticketId) {
        Ticket ticket = ticketService.findTicketById(ticketId);
        return new ResponseEntity<>(ticket, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<Ticket> updateTicket(@RequestBody Ticket ticket) {
        Ticket updatedTicket = ticketService.updateTicket(ticket);
        return new ResponseEntity<>(updatedTicket, HttpStatus.OK);
    }

    // buy tickets by its Event ID
    @PutMapping("/buy/{eventId}")
    public CompletableFuture<ResponseEntity<List<Ticket>>> buyTicket(@PathVariable Long eventId, @RequestBody BuyTicketDTO buyTicketDTO) {
        return ticketService.buyTicket(eventId, buyTicketDTO)
                .thenApply(ResponseEntity::getBody)
                .thenApply(tickets -> new ResponseEntity<>(tickets, HttpStatus.OK));
    }

    @DeleteMapping("/delete/{ticketId}")
    public ResponseEntity<?> deleteTicket(@PathVariable Long ticketId) {
        ticketService.deleteTicket(ticketId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
