import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Ticket, Event, AddTicketDTO, Vendor } from '../../model/model';
import { TicketService } from '../../service/ticket.service';
import { EventService } from '../../service/event.service';
import { VendorService } from '../../service/vendor.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-add-tickets',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule],
  templateUrl: './add-tickets.component.html',
  styleUrls: ['./add-tickets.component.css' ] 
})

export class AddTicketsComponent implements OnInit {
  addTicketForm!: FormGroup;
  events: Event[] = [];
  vendor!: Vendor;
  vendorId!: number;
  submitted = false;
  successMessage: string = '';
  previewTicket: Ticket | null = null;

  constructor(
    private fb: FormBuilder,
    private ticketService: TicketService,
    private eventService: EventService,
    private vendorService: VendorService,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.vendorId = this.route.snapshot.params['vendorId'];
    this.addTicketForm = this.fb.group({
      ticketPrice: [null, [Validators.required, Validators.min(1)]],
      ticketType: ['regular', [Validators.required]],
      ticketCount: [null, [Validators.required, Validators.min(1), Validators.max(100)]],
      event: [null, [Validators.required]]
    });

    this.loadVendorDetails();
    this.loadEvents();
  }

  loadVendorDetails(): void {
    this.vendorService.getVendorById(this.vendorId).subscribe(vendor => {
      this.vendor = vendor;
    });
  }

  loadEvents(): void {
    this.eventService.getAllEvents().subscribe(
      events => {
        this.events = events;
        console.log('Event List:', this.events); // Log event list for debugging
      },
      error => {
        console.error('Error fetching events:', error);
      }
    );
  }

  get f() {
    return this.addTicketForm.controls;
  }

  onSubmit(): void {
    this.submitted = true;
  
    if (this.addTicketForm.invalid) {
      this.submitted = false; // Reset if invalid
      return;
    }
  
    const selectedEvent = this.events.find(event => event.eventId === +this.addTicketForm.value.event);
  
    if (!selectedEvent) {
      console.error('Selected event not found');
      this.submitted = false; // Reset if event not found
      return;
    }
  
    const ticketData: AddTicketDTO = {
      ticket: {
        ticketId: 0,
        ticketPrice: this.addTicketForm.value.ticketPrice,
        ticketType: this.addTicketForm.value.ticketType,
        ticketStatus: 'available',
        ticketCode: '',
      },
      event: selectedEvent,
      vendor: this.vendor,
      ticketCount: this.addTicketForm.value.ticketCount
    };
  
    this.ticketService.addTicket(ticketData).subscribe({
      next: (response) => {
        this.submitted = false; // Reset loading state
        this.successMessage = 'Ticket added successfully!';
        this.previewTicket = response;
        this.addTicketForm.reset({
          ticketType: 'regular' // Reset with default value
        });
      },
      error: (error) => {
        this.submitted = false; // Reset loading state
        console.error('Error adding ticket:', error);
        this.successMessage = 'An error occurred while adding the ticket.';
      }
    });
  }

  addMoreTickets(): void {
    this.addTicketForm.reset();
    this.previewTicket = null;
    this.successMessage = '';
  }
}