import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { TicketService } from '../../service/ticket.service';
import { BuyTicketDTO } from '../../model/model';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';

@Component({
  selector: 'app-buy-tickets',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './buy-tickets.component.html',
  styleUrls: ['./buy-tickets.component.css']
})
export class BuyTicketsComponent implements OnInit {
  ticketForm!: FormGroup;
  successMessage: string = '';
  eventId!: number;

  constructor(
    private fb: FormBuilder,
    private ticketService: TicketService,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.eventId = this.route.snapshot.params['eventid'];
    this.ticketForm = this.fb.group({
      name: ['', [Validators.required, Validators.minLength(2)]],
      email: ['', [Validators.required, Validators.email]],
      phone: ['', [Validators.required, Validators.pattern(/^\d{3}-\d{3}-\d{4}$/)]],
      quantity: [null, [Validators.required, Validators.min(1)]]
    });
  }

  get f() {
    return this.ticketForm.controls;
  }

  onSubmit(): void {
    if (this.ticketForm.invalid) {
      return;
    }

    const buyTicketData: BuyTicketDTO = {
      customer: {
        customerId: 0, // Assuming customerId is auto-generated or not needed for the request
        firstName: this.ticketForm.value.name,
        email: this.ticketForm.value.email,
        phone: this.ticketForm.value.phone,
        customerCode: ''
      },
      ticketCount: this.ticketForm.value.quantity
    };

    console.log('Submitting buy ticket request:', buyTicketData);

    this.ticketService.buyTicket(this.eventId, buyTicketData).subscribe(
      (response) => {
        console.log('Buy ticket response:', response);
        this.successMessage = 'Thank you for buying tickets!';
        this.ticketForm.reset();
      },
      (error) => {
        console.error('Error:', error);
        this.successMessage = 'An error occurred while processing your request.';
      }
    );
  }
}