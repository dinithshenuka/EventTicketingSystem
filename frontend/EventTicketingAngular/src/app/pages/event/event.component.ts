import { Component, OnInit, inject } from '@angular/core';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { EventService } from '../../service/event.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-event',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './event.component.html',
  styleUrls: ['./event.component.css']
})
export class EventComponent implements OnInit {

  activatedRoute = inject(ActivatedRoute);  
  eventService = inject(EventService);
  eventData: any;

  constructor() {
    this.activatedRoute.params.subscribe((res: any) => {
      debugger;
    });
  }

  ngOnInit(): void {
    const eventId = this.activatedRoute.snapshot.params['id'];
    this.loadEventData(eventId);
  }

  loadEventData(eventId: number): void {
    this.eventService.getEventById(eventId).subscribe({
      next: (data) => {
        this.eventData = data;
        console.log('Event Data:', this.eventData);
      },
      error: (error) => {
        console.error('Error fetching event data:', error);
      }
    });
  }
}