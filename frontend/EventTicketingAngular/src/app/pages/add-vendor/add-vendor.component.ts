import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { VendorService } from '../../service/vendor.service';
import { Vendor } from '../../model/model';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';

@Component({
  selector: 'app-add-vendor',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, FormsModule],
  templateUrl: './add-vendor.component.html',
  styleUrls: ['./add-vendor.component.css']
})
export class AddVendorComponent implements OnInit {
  vendorForm!: FormGroup;
  successMessage: string = '';
  vendorId: number | null = null;
  vendors: Vendor[] = [];
  searchTerm: string = '';
  isLoading: boolean = true;

  constructor(
    private fb: FormBuilder,
    private vendorService: VendorService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.initForm();
    this.loadVendors();
  }

  private initForm(): void {
    this.vendorForm = this.fb.group({
      vendorName: ['', [Validators.required]],
      email: ['', [Validators.required, Validators.email]],
      phone: ['', [Validators.required, Validators.pattern(/^\+?\d{10,15}$/)]],
      companyName: ['', [Validators.required]],
      companyAddress: ['', [Validators.required]]
    });
  }

  private loadVendors(): void {
    this.isLoading = true;
    this.vendorService.getAllVendors().subscribe({
      next: (vendors) => {
        this.vendors = vendors;
        this.isLoading = false;
      },
      error: (error) => {
        console.error('Error loading vendors:', error);
        this.isLoading = false;
      }
    });
  }

  get f() {
    return this.vendorForm.controls;
  }

  get filteredVendors(): Vendor[] {
    return this.vendors.filter(vendor => 
      vendor.vendorName.toLowerCase().includes(this.searchTerm.toLowerCase()) ||
      vendor.companyName.toLowerCase().includes(this.searchTerm.toLowerCase())
    );
  }

  onSubmit(): void {
    if (this.vendorForm.invalid) return;

    const vendorData: Vendor = {
      vendorId: 0,
      vendorName: this.vendorForm.value.vendorName,
      email: this.vendorForm.value.email,
      phone: this.vendorForm.value.phone,
      companyName: this.vendorForm.value.companyName,
      companyAddress: this.vendorForm.value.companyAddress,
      vendorCode: ''
    };

    this.vendorService.addVendor(vendorData).subscribe({
      next: (response) => {
        this.successMessage = 'Signup complete. Do you want to add tickets now?';
        this.vendorId = response.vendorId;
        this.vendorForm.reset();
        this.loadVendors(); // Refresh vendor list
      },
      error: (error) => {
        console.error('Error:', error);
        this.successMessage = 'An error occurred while adding the vendor.';
        this.vendorId = null;
      }
    });
  }

  navigateToAddTickets(id: number = this.vendorId!): void {
    this.router.navigate(['/add-tickets', id]);
  }
}