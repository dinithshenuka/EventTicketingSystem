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

  ngOnInit(): void {
    this.getEvents();
  }

  getEvents(): void {
    debugger;
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

