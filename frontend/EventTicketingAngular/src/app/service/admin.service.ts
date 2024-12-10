import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AdminService {
  private apiUrl = 'http://localhost:8080/admin/';

  constructor(private http: HttpClient) {}

  startTicketHandling(): Observable<void> {
    return this.http.post<void>(`${this.apiUrl}start`, {});
  }

  stopTicketHandling(): Observable<void> {
    return this.http.post<void>(`${this.apiUrl}stop`, {});
  }
}