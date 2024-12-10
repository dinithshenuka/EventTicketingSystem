import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { VendorService } from '../../service/vendor.service';
import { Vendor } from '../../model/model';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';

@Component({
  selector: 'app-add-vendor',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './add-vendor.component.html',
  styleUrls: ['./add-vendor.component.css']
})
export class AddVendorComponent implements OnInit {
  vendorForm!: FormGroup;
  successMessage: string = '';
  vendorId: number | null = null;

  constructor(
    private fb: FormBuilder,
    private vendorService: VendorService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.vendorForm = this.fb.group({
      vendorName: ['', [Validators.required]], // Change this line
      email: ['', [Validators.required, Validators.email]],
      phone: ['', [Validators.required, Validators.pattern(/^\+?\d{10,15}$/)]],
      companyName: ['', [Validators.required]],
      companyAddress: ['', [Validators.required]]
    });
  }

  get f() {
    return this.vendorForm.controls;
  }

  onSubmit(): void {
    if (this.vendorForm.invalid) {
      return;
    }

    const vendorData: Vendor = {
      vendorId: 0, // initialised in bsckend
      vendorName: this.vendorForm.value.vendorName, 
      email: this.vendorForm.value.email,
      phone: this.vendorForm.value.phone,
      companyName: this.vendorForm.value.companyName,
      companyAddress: this.vendorForm.value.companyAddress,
      vendorCode: '' // initialised in backend
    };

    this.vendorService.addVendor(vendorData).subscribe(
      (response) => {
        this.successMessage = 'Signup complete. Do you want to add tickets now?';
        this.vendorId = response.vendorId;
        this.vendorForm.reset();
      },
      (error) => {
        console.error('Error:', error);
        this.successMessage = 'An error occurred while adding the vendor.';
        this.vendorId = null;
      }
    );
  }

  navigateToAddTickets(): void {
    if (this.vendorId !== null) {
      this.router.navigate(['/add-tickets', this.vendorId]);
    }
  }
}