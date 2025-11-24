
import { Routes } from '@angular/router';
import { OrderPos } from './page/order-pos/order-pos';
import { Login } from './login/login';
import { MainLayout } from './shared/layout/main-layout/main-layout';
import { AuthLayout } from './shared/layout/auth-layout/auth-layout';
import { RegisterComponent } from './register/register';
import { Customer } from './page/customer/customer';
import { Product } from './page/product/product';

export const routes: Routes = [
  {
    path: '',
    component: MainLayout,
    children: [
      { path: 'order-pos', component: OrderPos },
      { path: 'customer', component: Customer},
      { path: 'product', component: Product}
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

