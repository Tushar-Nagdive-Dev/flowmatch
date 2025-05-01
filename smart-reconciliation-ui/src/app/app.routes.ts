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
        loadComponent: () => import('./ui-impl/dash-view/components/dashboard/dashboard.component').then(c => c.DashboardComponent),
        canActivate: [AuthGuard],
        children: [
            {
                path: 'stats',
                loadComponent: () => import('./ui-impl/dash-view/components/dashboard/pages/statistics/statistics.component').then(c => c.StatisticsComponent)
            },
            {
                path: 'invoices',
                loadComponent: () => import('./ui-impl/dash-view/components/dashboard/pages/invoices/invoices.component').then(c => c.InvoicesComponent)
            },
            {
                path: 'reconciliation',
                loadComponent: () => import('./ui-impl/dash-view/components/dashboard/pages/reconciliation/reconciliation.component').then(c => c.ReconciliationComponent)
            },
            {
                path: 'users',
                loadComponent: () => import('./ui-impl/dash-view/components/dashboard/pages/users/users.component').then(c => c.UsersComponent)
            },
            {
                path: '',
                redirectTo: 'stats',
                pathMatch: 'full'
            }
        ]
    },
    {
        path: '**',
        redirectTo: 'login'
    }
];
