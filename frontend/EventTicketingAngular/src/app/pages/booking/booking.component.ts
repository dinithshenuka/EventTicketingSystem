import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { CustomerService } from '../../service/customer.service';
import { CommonModule } from '@angular/common';
import { Customer } from '../../model/model';

@Component({
  selector: 'app-booking',
  templateUrl: './booking.component.html',
  styleUrls: ['./booking.component.css'],
  standalone: true,
  imports: [FormsModule, CommonModule]
})
export class BookingComponent {
  customer: Partial<Customer> = {
    firstName: '',
    email: '',
    phone: ''
  };
  error: string | null = null;
  isLoading = false;

  constructor(private customerService: CustomerService, private router: Router) {}

  addCustomer(form: any): void {
    if (form.valid) {
      this.isLoading = true;
      this.customerService.addCustomer(this.customer).subscribe({
        next: (response) => {
          console.log('Customer added:', response);
          form.reset();
          this.error = null;
          this.isLoading = false;
          this.router.navigate(['/purchase']);
        },
        error: (err) => {
          console.error('Error:', err);
          this.error = typeof err === 'string' ? err : 'Failed to add customer';
          this.isLoading = false;
        }
      });
    } else {
      this.error = 'Please fill in all required fields';
    }
  }
}