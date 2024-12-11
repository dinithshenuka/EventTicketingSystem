// configuration.service.ts
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Configurations } from '../model/model';

@Injectable({
  providedIn: 'root'
})
export class ConfigurationService {
  private baseUrl = 'http://localhost:8080/configuration/'; 

  constructor(private http: HttpClient) {}

  // get
  getConfiguration(): Observable<Configurations> {
    return this.http.get<Configurations>(`${this.baseUrl}`);
  }

  // update
  updateConfiguration(config: Configurations): Observable<Configurations> {
    return this.http.put<Configurations>(`${this.baseUrl}update`, config);
  }

  // start
  startSimulation(): Observable<void> {
    return this.http.post<void>(`${this.baseUrl}start`, {});
  }

  // stop
  stopSimulation(): Observable<void> {
    return this.http.post<void>(`${this.baseUrl}stop`, {});
  }
}