import { Routes } from '@angular/router';
import { DashboardComponent } from './dashboard/dashboard.component';
import { HomepageComponent } from './homepage/homepage.component';
import { authGuard } from './shared/auth.guard';

export const routes: Routes = [
    { path: 'home', component: HomepageComponent },
    { path: '', redirectTo: '/home', pathMatch: 'full' },
    { path: 'dashboard', component: DashboardComponent, data: { userId: 'uniqueId' } , canActivate: [authGuard]},
];