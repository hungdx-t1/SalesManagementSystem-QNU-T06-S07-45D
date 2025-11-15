import { RouterModule, Routes } from '@angular/router';
import { OrderPos } from './page/order-pos/order-pos';
import { Login } from './login/login';
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

export const routes: Routes = [
  {path: 'order-pos', component: OrderPos},
  {path: 'login', component: Login}
];
@NgModule({
  declarations: [],
  imports: [
    BrowserModule,
    RouterModule.forRoot(routes),
    OrderPos,]
  })
  export class AppModule {}
