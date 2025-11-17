
import { Routes } from '@angular/router';
import { OrderPos } from './page/order-pos/order-pos';
import { SupplierComponent } from './page/supplier/supplier';
import { Login } from './login/login';
import { MainLayout } from './shared/layout/main-layout/main-layout';
import { AuthLayout } from './shared/layout/auth-layout/auth-layout';
import { RegisterComponent } from './register/register';

export const routes: Routes = [
  {
    path: '',
    component: MainLayout,
    children: [
      { path: 'order-pos', component: OrderPos },
      { path: 'supplier', component: SupplierComponent }
    ]
  },
  {
    path: '',
    component: AuthLayout,
    children: [
      { path: 'login', component: Login },
      { path: 'register', component: RegisterComponent }
    ]
  },
  { path: '**', redirectTo: 'login' }
];

