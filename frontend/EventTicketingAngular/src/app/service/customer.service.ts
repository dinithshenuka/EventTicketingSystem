import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError, retry } from 'rxjs/operators';
import { Customer } from '../model/model';

@Injectable({
  providedIn: 'root'
})
export class CustomerService {
  private baseUrl = 'http://localhost:8080/customer/';

  constructor(private http: HttpClient) {}

  addCustomer(customer: Partial<Customer>): Observable<Customer> {
    return this.http.post<Customer>(`${this.baseUrl}add`, customer).pipe(
      retry(3),
      catchError(this.handleError)
    );
  }

  private handleError(error: HttpErrorResponse) {
    let errorMsg = 'An unknown error occurred!';
    if (error.error instanceof ErrorEvent) {
      // Client-side error
      errorMsg = `Error: ${error.error.message}`;
    } else {
      // Server-side error
      errorMsg = `Error Code: ${error.status}\nMessage: ${error.message}`;
      if (error.status === 0) {
        errorMsg = 'Server is not responding. Please verify backend service is running.';
      }
    }
    console.error(errorMsg);
    return throwError(() => errorMsg);
  }
}