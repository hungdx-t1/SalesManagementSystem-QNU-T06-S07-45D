import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { CommonModule, NgFor, NgClass, CurrencyPipe, TitleCasePipe } from '@angular/common';
import { Services } from '../../services';
@Component({
  selector: 'app-order-pos',
  standalone: true,
  imports: [CommonModule, NgFor, NgClass, CurrencyPipe, TitleCasePipe, HttpClientModule],
  templateUrl: './order-pos.html',
  styleUrls: ['./order-pos.css']
})
export class OrderPos implements OnInit {
  payments: any[] = [];
  constructor(private services: Services, private http: HttpClient) {}

  ngOnInit() {
  const apiUrl = `${this.services.apiUrl}/api/payments/details`;
  const token = localStorage.getItem('jwt'); // token đã lưu sau login

  this.http.get<any[]>(apiUrl, {
    headers: { Authorization: `Bearer ${token}` }
  }).subscribe({
    next: (data) => {
      console.log('Payments data:', data);
      this.payments = data;
    },
    error: (err) => console.error('Error fetching payments', err)
  });
}


}
