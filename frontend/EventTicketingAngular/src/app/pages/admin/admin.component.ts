import { Component, OnInit, OnDestroy, inject, PLATFORM_ID } from '@angular/core';
import { CommonModule, isPlatformBrowser } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { TicketService } from '../../service/ticket.service';
import { VendorService } from '../../service/vendor.service';
import { ConfigurationService } from '../../service/configuration.service';
import { Subject, interval } from 'rxjs';
import { takeUntil, switchMap } from 'rxjs/operators';
import { Configurations } from '../../model/model';

@Component({
  selector: 'app-admin',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})

export class AdminComponent implements OnInit, OnDestroy {
  private destroy$ = new Subject<void>();
  private readonly platformId = inject(PLATFORM_ID);
  private fb = inject(FormBuilder);
  
  ticketService = inject(TicketService);
  vendorService = inject(VendorService);
  configService = inject(ConfigurationService);

  tickets: any[] = [];
  vendors: any[] = [];
  isLoading = true;
  isSimulationRunning = false;
  message = '';
  configForm!: FormGroup;

  ngOnInit(): void {
    this.initConfigForm();
    // this.loadConfiguration();
    if (isPlatformBrowser(this.platformId)) {
      this.startPolling();
    }
  }

  private initConfigForm(): void {
    this.configForm = this.fb.group({
      totalTickets: ['', [Validators.required, Validators.min(1)]],
      maxTicketCapacity: ['', [Validators.required, Validators.min(1)]],
      ticketReleaseRate: ['', [Validators.required, Validators.min(1), Validators.max(5)]],
      customerRetrivalRate: ['', [Validators.required, Validators.min(1), Validators.max(5)]]
    }, { validator: this.totalTicketsValidator });
  }

  private totalTicketsValidator(group: FormGroup) {
    const total = group.get('totalTickets')?.value;
    const max = group.get('maxTicketCapacity')?.value;
    if (total && max) {
      return total < max ? { totalExceedsMax: true } : null;
    }
    return null;
  }

  // private loadConfiguration(): void {
  //   this.configService.getConfiguration().subscribe({
  //     next: (config: Configurations) => {
  //       this.configForm.patchValue(config);
  //     },
  //     error: () => {
  //       this.message = 'Failed to load configuration';
  //     }
  //   });
  // }

  startPolling(): void {
    // Poll tickets
    interval(1000).pipe(
      switchMap(() => this.ticketService.getAllTickets()),
      takeUntil(this.destroy$)
    ).subscribe({
      next: (tickets) => {
        this.tickets = tickets;
        this.isLoading = false;
      },
      error: (error) => console.error('Ticket polling error:', error)
    });

    // Poll vendors
    interval(3000).pipe(
      switchMap(() => this.vendorService.getAllVendors()),
      takeUntil(this.destroy$)
    ).subscribe({
      next: (vendors) => {
        this.vendors = vendors;
        this.isLoading = false;
      },
      error: (error) => console.error('Vendor polling error:', error)
    });
  }

  toggleSimulation(): void {
    if (this.isSimulationRunning) {
      this.configService.stopSimulation().subscribe({
        next: () => {
          this.isSimulationRunning = false;
          this.message = 'Simulation stopped';
        },
        error: () => this.message = 'Failed to stop simulation'
      });
    } else {
      this.configService.startSimulation().subscribe({
        next: () => {
          this.isSimulationRunning = true;
          this.message = 'Simulation started';
        },
        error: () => this.message = 'Failed to start simulation'
      });
    }
  }

  saveConfig(): void {
    if (this.configForm.valid) {
      this.configService.updateConfiguration(this.configForm.value).subscribe({
        next: () => this.message = 'Configuration saved successfully',
        error: () => this.message = 'Failed to save configuration'
      });
    }
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
}
}