import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { CommonModule, NgFor, NgClass, CurrencyPipe, TitleCasePipe } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Services } from '../../services';

interface Order {
  id: number;
  customer: { name: string; phone?: string };
  totalAmount: number;
  status: string;
}

@Component({
  selector: 'app-order-pos',
  standalone: true,
  imports: [CommonModule, NgFor, NgClass, CurrencyPipe, TitleCasePipe, HttpClientModule, FormsModule],
  templateUrl: './order-pos.html',
  styleUrls: ['./order-pos.css']
})
export class OrderPos implements OnInit {
  orders: Order[] = [];
  filteredOrders: Order[] = [];
  selectedOrder: Order | null = null;
  selectedPaymentMethod: string = 'cash';
  public searchTerm: string = ''; // Từ khóa tìm kiếm

  constructor(private services: Services, private http: HttpClient) {}

  ngOnInit() {
    this.loadOrders();
  }

  // Load danh sách đơn hàng
  loadOrders() {
    const token = localStorage.getItem('jwt');
    this.http.get<Order[]>(`${this.services.apiUrl}/api/orders`, {
      headers: { Authorization: `Bearer ${token}` }
    }).subscribe({
      next: (data) => {
        this.orders = data;
        this.filteredOrders = [...data]; // Clone để giữ nguyên danh sách gốc
        this.applyFilter(); // Áp dụng lọc nếu có từ khóa
      },
      error: (err) => console.error('Error fetching orders', err)
    });
  }

  // Hàm tìm kiếm - chỉ gọi khi nhấn nút
  search() {
    this.applyFilter();
  }

  // Lọc đơn hàng
  private applyFilter() {
    const term = this.searchTerm.trim().toLowerCase();
    if (!term) {
      this.filteredOrders = [...this.orders];
    } else {
      this.filteredOrders = this.orders.filter(order =>
        order.customer.name.toLowerCase().includes(term) ||
        (order.customer.phone && order.customer.phone.includes(term))
      );
    }
  }

  // Mở modal thanh toán
  openPaymentModal(order: Order) {
    this.selectedOrder = order;
    this.selectedPaymentMethod = 'cash';
  }

  // Đóng modal
  closeModal() {
    this.selectedOrder = null;
  }

  // Xác nhận thanh toán
  confirmPayment() {
    if (!this.selectedOrder) return;

    const token = localStorage.getItem('jwt');
    const apiUrl = `${this.services.apiUrl}/api/orders/${this.selectedOrder.id}/pay`;
    const params = {
      amount: this.selectedOrder.totalAmount,
      method: this.selectedPaymentMethod
    };

    this.http.post(apiUrl, null, {
      headers: { Authorization: `Bearer ${token}` },
      params
    }).subscribe({
      next: () => {
        alert('Thanh toán thành công!');
        this.closeModal();
        this.loadOrders();
      },
      error: (err) => {
        console.error(err);
        alert('Thanh toán thất bại!');
      }
    });
  }
}
