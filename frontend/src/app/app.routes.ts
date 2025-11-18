
import { Routes } from '@angular/router';

import { OrderPos } from './page/order-pos/order-pos';
import { Login } from './login/login';
import { MainLayout } from './shared/layout/main-layout/main-layout';
import { AuthLayout } from './shared/layout/auth-layout/auth-layout';
import { RegisterComponent } from './register/register';

export const routes: Routes = [
  // Auth layout (login, register)
  {
    path: '',
    component: AuthLayout,
    children: [
      { path: '', redirectTo: 'login', pathMatch: 'full' },
      { path: 'login', component: Login },
      { path: 'register', component: RegisterComponent }
    ]
  },

  // Main layout (sau khi đăng nhập)
  {
    path: 'app',
    component: MainLayout,
    children: [
      { path: 'order-pos', component: OrderPos }
    ]
  },
  { path: '**', redirectTo: 'login' }
];

