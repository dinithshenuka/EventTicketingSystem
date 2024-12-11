import { Component, OnInit, OnDestroy, inject, PLATFORM_ID, Inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { TicketService } from '../../service/ticket.service';
import { EventService } from '../../service/event.service';
import { BuyTicketDTO } from '../../model/model';
import { CommonModule, isPlatformBrowser } from '@angular/common';
import { Subject, interval } from 'rxjs';
import { takeUntil, switchMap } from 'rxjs/operators';

@Component({
  selector: 'app-buy-tickets',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './buy-tickets.component.html',
  styleUrls: ['./buy-tickets.component.css']
})
export class BuyTicketsComponent implements OnInit, OnDestroy {
  private destroy$ = new Subject<void>();
  private readonly platformId = inject(PLATFORM_ID);

  ticketForm!: FormGroup;
  eventData: any;
  ticketCount: number = 0;
  isLoading: boolean = true;
  isProcessing: boolean = false;
  successMessage: string = '';
  errorMessage: string = '';
  eventId!: number;

  constructor(
    private fb: FormBuilder,
    private ticketService: TicketService,
    private eventService: EventService,
    private route: ActivatedRoute,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.eventId = this.route.snapshot.params['eventid'];
    this.initForm();
    this.loadEventData();

    if (isPlatformBrowser(this.platformId)) {
      this.startTicketPolling();
    }
  }

  private initForm(): void {
    this.ticketForm = this.fb.group({
      name: ['', [Validators.required, Validators.minLength(2)]],
      email: ['', [Validators.required, Validators.email]],
      phone: ['', [Validators.required, Validators.pattern(/^\d{3}-\d{3}-\d{4}$/)]],
      quantity: [1, [Validators.required, Validators.min(1)]]
    });
  }

  loadEventData(): void {
    this.eventService.getEventById(this.eventId).subscribe({
      next: (data) => {
        this.eventData = data;
        this.isLoading = false;
      },
      error: (error) => {
        console.error('Error loading event:', error);
        this.isLoading = false;
      }
    });
  }

  startTicketPolling(): void {
    interval(3000).pipe(
      switchMap(() => this.ticketService.ticketCountForEvent(this.eventId)),
      takeUntil(this.destroy$)
    ).subscribe({
      next: (count) => this.ticketCount = count,
      error: (error) => console.error('Polling error:', error)
    });
  }

  onSubmit(): void {
    if (this.ticketForm.invalid || this.isProcessing) return;

    this.isProcessing = true;
    this.errorMessage = '';

    const buyTicketData: BuyTicketDTO = {
      customer: {
        customerId: 0, // backend thing
        firstName: this.ticketForm.value.name,
        email: this.ticketForm.value.email,
        phone: this.ticketForm.value.phone,
        customerCode: '' // backend
      },
      ticketCount: this.ticketForm.value.quantity
    };

    this.ticketService.buyTicket(this.eventId, buyTicketData).subscribe({
      next: (response) => {
        this.isProcessing = false;
        this.successMessage = 'Tickets booked successfully!';
        this.ticketForm.reset();
        this.destroy$.next(); // Stop polling
      },
      error: (error) => {
        this.isProcessing = false;
        this.errorMessage = 'Failed to book tickets. Please try again.';
        console.error('Booking error:', error);
      }
    });
  }

  retry(): void {
    this.errorMessage = '';
    this.onSubmit();
  }

  get f() {
    return this.ticketForm.controls;
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }

  navigateToEvents(): void {
    this.router.navigate(['/event-page']);
  }
}