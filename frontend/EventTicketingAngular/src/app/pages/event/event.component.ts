import { Component, OnInit, OnDestroy, inject, PLATFORM_ID, Inject } from '@angular/core';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { EventService } from '../../service/event.service';
import { TicketService } from '../../service/ticket.service';
import { CommonModule, isPlatformBrowser } from '@angular/common';
import { Subject, interval } from 'rxjs';
import { takeUntil, switchMap } from 'rxjs/operators';

@Component({
  selector: 'app-event',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './event.component.html',
  styleUrls: ['./event.component.css']
})
export class EventComponent implements OnInit, OnDestroy {
  private destroy$ = new Subject<void>();
  private readonly platformId = inject(PLATFORM_ID);
  activatedRoute = inject(ActivatedRoute);
  eventService = inject(EventService);
  ticketService = inject(TicketService);
  
  eventData: any;
  ticketCount: number = 0;
  isLoading: boolean = true;

  ngOnInit(): void {
    const eventId = this.activatedRoute.snapshot.params['id'];
    this.loadEventData(eventId);
    
    if (isPlatformBrowser(this.platformId)) {
      this.startTicketPolling(eventId);
    }
  }

  loadEventData(eventId: number): void {
    this.eventService.getEventById(eventId).subscribe({
      next: (data) => {
        this.eventData = data;
        console.log('Event Data:', this.eventData);
        
        if (isPlatformBrowser(this.platformId)) {
          this.ticketService.ticketCountForEvent(eventId).subscribe({
            next: (count) => {
              this.ticketCount = count;
              this.isLoading = false;
            }
          });
        }
      },
      error: (error) => {
        console.error('Error fetching event data:', error);
        this.isLoading = false;
      }
    });
  }

  startTicketPolling(eventId: number): void {
    interval(2000)
      .pipe(
        switchMap(() => this.ticketService.ticketCountForEvent(eventId)),
        takeUntil(this.destroy$)
      )
      .subscribe({
        next: (count) => {
          this.ticketCount = count;
          this.isLoading = false;
        },
        error: (error) => {
          console.error('Error polling ticket count:', error);
          this.isLoading = false;
        }
      });
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }
}