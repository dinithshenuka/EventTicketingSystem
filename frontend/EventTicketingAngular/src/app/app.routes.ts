import { Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { EventComponent } from './pages/event/event.component';
import { MyBookingComponent } from './pages/my-booking/my-booking.component';
import { EventPageComponent } from './pages/event-page/event-page.component';

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
        path: 'my-booking',
        component: MyBookingComponent
    },
    {
        path: 'event-page',
        component: EventPageComponent
    }
];