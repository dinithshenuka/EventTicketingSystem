import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Event } from '../model/model';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class EventService {

  apiUrl = 'http://localhost:8080/event/';

  constructor(private http: HttpClient) { }

  // Get all events
  public getAllEvents(): Observable<Event[]> {
    return this.http.get<Event[]>(`${this.apiUrl}all`);
  }

  // Get event by id
  public getEventById(eventId: number): Observable<Event> {
    return this.http.get<Event>(`${this.apiUrl}find/${eventId}`);
  }

  // add event 
  public addEvent(event: Event): Observable<Event> {
    return this.http.post<Event>(`${this.apiUrl}add`, event);
  }

  // update event
  public updateEvent(event: Event): Observable<Event> {
    return this.http.put<Event>(`${this.apiUrl}update`, event);
  }

  // delete event
  public deleteEvent(eventId: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}delete/${eventId}`);
  }
  
}
