import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Vendor } from '../model/model';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class VendorService {

  private apiUrl = 'http://localhost:8080/vendor/';

  constructor(private http: HttpClient) { }

  // Get all vendors
  public getAllVendors(): Observable<Vendor[]> {
    return this.http.get<Vendor[]>(`${this.apiUrl}all`);
  }

  // Get vendor by id
  public getVendorById(vendorId: number): Observable<Vendor> {
    return this.http.get<Vendor>(`${this.apiUrl}find/${vendorId}`);
  }

  // Add vendor
  public addVendor(vendor: Vendor): Observable<Vendor> {
    return this.http.post<Vendor>(`${this.apiUrl}add`, vendor);
  }

  // Update vendor
  public updateVendor(vendor: Vendor): Observable<Vendor> {
    return this.http.put<Vendor>(`${this.apiUrl}update`, vendor);
  }

  // Delete vendor
  public deleteVendor(vendorId: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}delete/${vendorId}`);
  }


}
