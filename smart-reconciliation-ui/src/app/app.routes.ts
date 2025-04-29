import { Routes } from '@angular/router';
import { AuthGuard } from './ui-impl/auth/gaurds/auth.guard';

export const routes: Routes = [
    {
        path: '',
        redirectTo: 'login',
        pathMatch: 'full'
    },
    {
        path: 'login',
        loadComponent: () => import('./ui-impl/auth/components/login/login.component').then(c => c.LoginComponent)
    },
    {
        path: 'register',
        loadComponent: () => import('./ui-impl/auth/components/register/register.component').then(c => c.RegisterComponent)
    },
    {
        path: 'dashboard',
        loadComponent: () => import('./ui-impl/auth/components/dashboard/dashboard.component').then(c => c.DashboardComponent),
        canActivate: [AuthGuard]
    },
    {
        path: '**',
        redirectTo: 'login'
    }
];
