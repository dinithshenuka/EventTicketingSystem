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
  getAllEvents(): Observable<Event[]> {
    debugger;
    return this.http.get<Event[]>(`${this.apiUrl}all`);
  }
}
