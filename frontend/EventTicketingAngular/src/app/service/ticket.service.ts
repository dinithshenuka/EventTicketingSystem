import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AddTicketDTO, AllTickets, BuyTicketDTO, Ticket } from '../model/model';

@Injectable({
  providedIn: 'root'
})
export class TicketService {

  private apiUrl = 'http://localhost:8080/ticket/';

  constructor(private http:HttpClient) { }

  // Get all tickets (Database and pool)
  public getAllTickets(): Observable<AllTickets[]> {
    return this.http.get<AllTickets[]>(`${this.apiUrl}all`);
  }

  // Get ticket by id
  public getTicketById(ticketId: number): Observable<Ticket> {
    return this.http.get<Ticket>(`${this.apiUrl}find/${ticketId}`);
  }

  // Add ticket 
  public addTicket(ticketData: AddTicketDTO): Observable<any> {
    return this.http.post<AddTicketDTO>(`${this.apiUrl}add`, ticketData);
  }

  // get all tickets in pool (without database tickets)
  public getAllTicketsInPool(): Observable<Ticket[]> {
    return this.http.get<Ticket[]>(`${this.apiUrl}all/pool`);
  }

  // ticket count in pool
  public ticketCountInPool(): Observable<number> {
    return this.http.get<number>(`${this.apiUrl}count`);
  }

  // get ticket count for specific event
  public ticketCountForEvent(eventId: number): Observable<number> {
    return this.http.get<number>(`${this.apiUrl}count/${eventId}`);
  }

  // get ticket all tickets specific event
  public getAllTicketsForEvent(eventId: number): Observable<Ticket[]> {
    return this.http.get<Ticket[]>(`${this.apiUrl}all/${eventId}`);
  }

  // find ticket
  public findTicket(ticketId: number): Observable<Ticket> {
    return this.http.get<Ticket>(`${this.apiUrl}find/${ticketId}`);
  }

  // update ticket
  public updateTicket(ticket: Ticket): Observable<Ticket> {
    return this.http.put<Ticket>(`${this.apiUrl}update`, ticket);
  }

  // buy ticket by event id 
  public buyTicket(eventId: number, buyTicketDTO: BuyTicketDTO): Observable<Ticket> {
    return this.http.put<Ticket>(`${this.apiUrl}buy/${eventId}`, buyTicketDTO);
}

  // delete ticket
  public deleteTicket(ticketId: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}delete/${ticketId}`);
  }

}
