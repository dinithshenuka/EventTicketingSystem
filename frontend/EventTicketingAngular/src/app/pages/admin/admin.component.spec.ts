import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { AdminComponent } from './admin.component';
import { TicketService } from '../../service/ticket.service';
import { VendorService } from '../../service/vendor.service';
import { AdminService } from '../../service/admin.service';
import { WebSocketService } from '../../service/web-socket.service';
import { of, Subject } from 'rxjs';

describe('AdminComponent', () => {
  let component: AdminComponent;
  let fixture: ComponentFixture<AdminComponent>;
  let messageSubject: Subject<string>;

  beforeEach(async () => {
    messageSubject = new Subject<string>();

    await TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule],
      declarations: [AdminComponent],
      providers: [
        { provide: TicketService, useValue: { getAllTickets: () => of([]) } },
        { provide: VendorService, useValue: { getAllVendors: () => of([]) } },
        { provide: AdminService, useValue: {} },
        { provide: WebSocketService, useValue: { getMessages: () => messageSubject.asObservable() } }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(AdminComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should handle WebSocket TICKET_UPDATE message', () => {
    spyOn(component, 'fetchTickets');
    messageSubject.next(JSON.stringify({ type: 'TICKET_UPDATE' }));
    expect(component.fetchTickets).toHaveBeenCalled();
  });

  it('should handle WebSocket VENDOR_UPDATE message', () => {
    spyOn(component, 'fetchVendors');
    messageSubject.next(JSON.stringify({ type: 'VENDOR_UPDATE' }));
    expect(component.fetchVendors).toHaveBeenCalled();
  });
});