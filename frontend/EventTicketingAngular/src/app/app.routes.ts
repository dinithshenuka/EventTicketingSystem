import { Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { EventComponent } from './pages/event/event.component';
import { EventPageComponent } from './pages/event-page/event-page.component';
import { AddTicketsComponent } from './pages/add-tickets/add-tickets.component';
import { BuyTicketsComponent } from './pages/buy-tickets/buy-tickets.component';
import { AddVendorComponent } from './pages/add-vendor/add-vendor.component';
import { AdminComponent } from './pages/admin/admin.component';

export const routes: Routes = [
    {
        path: '',
        redirectTo: 'home',
        pathMatch: 'full'
    },
    {
        path: 'home',
        component: HomeComponent
    },
    {
        path: 'event/:id',
        component: EventComponent
    },
    {
        path: 'event-page',
        component: EventPageComponent
    },
    {
        path: 'add-tickets/:vendorId',
        component: AddTicketsComponent
    },
    {
        path: 'buy-tickets/:eventid',
        component: BuyTicketsComponent
    },
    {
        path: 'add-vendor',
        component: AddVendorComponent
    },
    { 
        path:'admin',
        component:AdminComponent
    }
];