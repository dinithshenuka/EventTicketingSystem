import { Component, inject, Inject, OnInit } from '@angular/core';
import { EventService } from '../../service/event.service';
import { Event } from '../../model/model';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-event-page',
  standalone: true,
  imports: [RouterLink],
  templateUrl: './event-page.component.html',
  styleUrl: './event-page.component.css'
})
export class EventPageComponent implements OnInit {

  eventService = inject(EventService);
  eventList: Event[] = [];
  cardColors: string[] = [
    '#ffadad', // light red
    '#ffd6a5', // light orange
    '#fdffb6', // light yellow
    '#caffbf'  // light green
  ];
  
  getCardColor(index: number): string {
    return this.cardColors[index % this.cardColors.length];
  }

  ngOnInit(): void {
    this.getEvents();
  }

  getEvents(): void {
    this.eventService.getAllEvents().subscribe({
      next:(data) => {
        this.eventList = Array.isArray(data) ? data : [];
        console.log('Event List:', this.eventList); // Log the eventList array
      },
      error:(error) => {
        console.log('Error fetching events:', error);
      }
    });
  }
}

