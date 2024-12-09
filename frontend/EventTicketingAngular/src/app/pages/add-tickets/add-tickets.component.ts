import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Ticket, Event, AddTicketDTO, Vendor } from '../../model/model';
import { TicketService } from '../../service/ticket.service';
import { EventService } from '../../service/event.service';
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
  vendors: Vendor[] = [];
  submitted = false;
  successMessage: string = '';
  previewTicket: Ticket | null = null;

  constructor(
    private fb: FormBuilder,
    private ticketService: TicketService,
    private eventService: EventService
  ) {}

  ngOnInit(): void {
    this.addTicketForm = this.fb.group({
      ticketPrice: [null, [Validators.required, Validators.min(1)]],
      ticketType: ['regular', [Validators.required]],
      ticketCount: [null, [Validators.required, Validators.min(1), Validators.max(100)]],
      event: [null, [Validators.required]]
    });

    this.loadEvents();
  }

  loadEvents(): void {
    this.eventService.getAllEvents().subscribe(events => {
      this.events = events;
    });
  }

  get f() {
    return this.addTicketForm.controls;
  }

  onSubmit(): void {
    this.submitted = true;

    if (this.addTicketForm.invalid) {
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
      event: {
        eventId: this.addTicketForm.value.event,
        eventName: '',
        eventLocation: '',
        eventDate: '',
        eventTime:'',
        eventCode:'',
        eventImgUrl: '',
      },
      vendor: {
        vendorId: this.addTicketForm.value.vendor,
        firstName: '',
        email: '',
        phone: '',
        companyName: '',
        companyAddress: '',
        vendorCode: '',
      },
      ticketCount: this.addTicketForm.value.ticketCount
    };

    this.ticketService.addTicket(ticketData).subscribe(
      (response) => {
        this.successMessage = 'Ticket added successfully!';
        this.previewTicket = response; // Show preview of submitted ticket
        this.addTicketForm.reset();
      },
      (error) => {
        this.successMessage = 'An error occurred while adding the ticket.';
      }
    );
  }

  addMoreTickets(): void {
    this.addTicketForm.reset();
    this.previewTicket = null;
    this.successMessage = '';
  }
}