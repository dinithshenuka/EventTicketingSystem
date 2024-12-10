import { Component, OnInit } from '@angular/core';
import { TicketService } from '../../service/ticket.service';
import { VendorService } from '../../service/vendor.service';
import { AdminService } from '../../service/admin.service';
import { AllTickets, Vendor } from '../../model/model';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { WebSocketService } from '../../service/web-socket.service';

@Component({
  selector: 'app-admin',
  standalone: true,
  imports: [CommonModule, FormsModule, HttpClientModule],
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent implements OnInit {
  isRunning = false;
  isLoaded = true;
  tickets: AllTickets[] = [];
  vendors: Vendor[] = [];
  errorMessage: string = '';

  constructor(
    private ticketService: TicketService,
    private vendorService: VendorService,
    private adminService: AdminService,
    private webSocketService: WebSocketService
  ) { }

  ngOnInit() {
    this.fetchTickets();
    this.fetchVendors();
    this.subscribeToWebSocket();
  }

  toggleStartStop() {
    const action = this.isRunning ? this.adminService.stopTicketHandling() : this.adminService.startTicketHandling();
    action.subscribe({
      next: () => {
        this.isRunning = !this.isRunning;
      },
      error: (err) => {
        this.errorMessage = `Failed to ${this.isRunning ? 'stop' : 'start'} ticket handling: ${err.message}`;
      }
    });
  }

  fetchTickets() {
    this.ticketService.getAllTickets().subscribe({
      next: (data: AllTickets[]) => {
        console.log('Ticket fetched');
        this.tickets = data;
        this.isLoaded = true; // Set isLoaded to true after fetching tickets
      },
      error: (err) => {
        this.errorMessage = `Failed to fetch tickets: ${err.message}`;
        this.isLoaded = false;
      }
    });
  }

  fetchVendors() {
    this.vendorService.getAllVendors().subscribe({
      next: (data: Vendor[]) => {
        console.log('Vendors fetched');
        this.vendors = data;
        this.isLoaded = true; // Set isLoaded to true after fetching vendors
      },
      error: (err) => {
        this.errorMessage = `Failed to fetch vendors: ${err.message}`;
        this.isLoaded = false;
      }
    });
  }

  subscribeToWebSocket() {
    this.webSocketService.getMessages().subscribe((message: string) => {
      console.log('Received WebSocket message:', message);
      const parsedMessage = JSON.parse(message);
      if (parsedMessage.type === 'TICKET_UPDATE') {
        this.fetchTickets();
      } else if (parsedMessage.type === 'VENDOR_UPDATE') {
        this.fetchVendors();
      }
    });
  }
}