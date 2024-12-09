import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Customer } from '../model/model';

@Injectable({
  providedIn: 'root'
})
export class CustomerService {
  
  private baseUrl = 'http://localhost:8080/customer/';

  constructor(private http: HttpClient) {}

  // Get all customers
  public getAllCustomers(): Observable<Customer[]> {
    debugger
    return this.http.get<Customer[]>(`${this.baseUrl}all`);
  }
  
  // Get customer by id
  public getCustomerById(customerId: number): Observable<Customer> {
    return this.http.get<Customer>(`${this.baseUrl}find/${customerId}`);
  }

  // Add customer
  public addCustomer(customer: Customer): Observable<Customer> {
    return this.http.post<Customer>(`${this.baseUrl}add`, customer);
  }

  // update customer
  public updateCustomer(customer: Customer): Observable<Customer> {
    return this.http.put<Customer>(`${this.baseUrl}update`, customer);
  }

  // delete customer
  public deleteCustomer(customerId: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}delete/${customerId}`);
  }
}