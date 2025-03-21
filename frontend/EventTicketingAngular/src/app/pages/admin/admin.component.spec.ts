import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { AdminComponent } from './admin.component';
import { TicketService } from '../../service/ticket.service';
import { VendorService } from '../../service/vendor.service';
import { AdminService } from '../../service/admin.service';
import { of } from 'rxjs';

describe('AdminComponent', () => {
  let component: AdminComponent;
  let fixture: ComponentFixture<AdminComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule],
      declarations: [AdminComponent],
      providers: [
        { provide: TicketService, useValue: { getAllTickets: () => of([]) } },
        { provide: VendorService, useValue: { getAllVendors: () => of([]) } },
        { provide: AdminService, useValue: {} }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(AdminComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});