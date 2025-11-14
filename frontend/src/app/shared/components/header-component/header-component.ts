import { Component } from '@angular/core';
import { Router } from '@angular/router';
@Component({
  selector: 'app-header-component',
  imports: [],
  templateUrl: './header-component.html',
  styleUrl: './header-component.css'
})
export class HeaderComponent {
  constructor(private router: Router) {}
goToLogin() {
    this.router.navigate(['/login']); // ⚡ chuyển đến trang login
  }
}
