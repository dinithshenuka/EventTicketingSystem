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
  styleUrls: ['./add-tickets.component.css']
})
export class AddTicketsComponent implements OnInit {
  addTicketForm!: FormGroup;
  events: Event[] = [];
  vendor!: Vendor;
  vendorId!: number;
  submitted = false;
  successMessage: string = '';
  errorMessage: string = '';
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
    this.initializeForm();
    this.loadVendorDetails();
    this.loadEvents();
  }

  private initializeForm(): void {
    this.addTicketForm = this.fb.group({
      ticketPrice: [null, [Validators.required, Validators.min(1)]],
      ticketType: ['regular', [Validators.required]],
      ticketCount: [null, [Validators.required, Validators.min(1), Validators.max(100)]],
      event: [null, [Validators.required]]
    });
  }

  loadVendorDetails(): void {
    this.vendorService.getVendorById(this.vendorId).subscribe({
      next: (vendor) => {
        this.vendor = vendor;
      },
      error: (error) => {
        this.errorMessage = 'Error loading vendor details';
        console.error('Error loading vendor:', error);
      }
    });
  }

  loadEvents(): void {
    this.eventService.getAllEvents().subscribe({
      next: (events) => {
        this.events = events;
      },
      error: (error) => {
        this.errorMessage = 'Error loading events';
        console.error('Error fetching events:', error);
      }
    });
  }

  get f() {
    return this.addTicketForm.controls;
  }

  onSubmit(): void {
    this.submitted = true;
    this.clearMessages();

    if (this.addTicketForm.invalid) {
      this.submitted = false;
      return;
    }

    const selectedEvent = this.events.find(event => event.eventId === +this.addTicketForm.value.event);

    if (!selectedEvent) {
      this.errorMessage = 'Selected event not found';
      this.submitted = false;
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
        this.submitted = false;
        this.successMessage = 'Tickets added successfully!';
        this.previewTicket = response;
        this.resetForm();
      },
      error: (error) => {
        this.submitted = false;
        this.errorMessage = error?.message || 'An error occurred while adding the tickets';
        console.error('Error adding ticket:', error);
      }
    });
  }

  private clearMessages(): void {
    this.successMessage = '';
    this.errorMessage = '';
  }

  private resetForm(): void {
    this.addTicketForm.reset({
      ticketType: 'regular'
    });
  }

  addMoreTickets(): void {
    this.resetForm();
    this.previewTicket = null;
    this.clearMessages();
  }
}