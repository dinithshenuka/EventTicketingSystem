export interface Event {
    eventId: number;
    eventName: string;
    eventLocation: string;
    eventDate: string;
    eventTime: string;
    eventCode: string;
    eventImgUrl: string;
}

export interface Customer {
    customerId: number;
    firstName: string;
    email: string;
    phone: string;
    customerCode: string;
}

export interface Ticket {
    ticketId: number;
    ticketPrice: number;
    ticketType: string;
    ticketStatus: string;
    ticketCode: string;
}

export interface Vendor {
    vendorId: number;
    vendorName: string;
    email: string;
    phone: string;
    companyName: string;
    companyAddress: string;
    vendorCode: string;
}

export interface AddTicketDTO {
    ticket: Ticket;
    event: Event;
    vendor: Vendor;
    ticketCount: number;
}

export interface BuyTicketDTO {
    customer: Customer;
    ticketCount: number;
}

export interface Configurations {
    totalTickets: number;
    ticketReleaseRate: number;
    customerRetrivalRate: number;
    maxTicketCapacity: number;
}