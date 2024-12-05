import { Component, inject, Inject, OnInit } from '@angular/core';
import { EventService } from '../../service/event.service';
import { Event } from '../../model/model';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [],
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

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
