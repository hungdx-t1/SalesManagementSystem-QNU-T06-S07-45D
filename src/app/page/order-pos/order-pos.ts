import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { CommonModule, NgFor, NgClass, CurrencyPipe, TitleCasePipe } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Services } from '../../services';

interface Order {
  id: number;
  customer: { id: number; name: string; phone?: string };
  totalAmount: number;
  status: string;
  items: OrderItem[];
  orderDate: string;
}
interface OrderItem {
  product: { name: string; category: { name: string }; price: number};
  quantity: number;
  unitPrice: number;

}

interface OrderDetail extends Order {
  items: OrderItem[];
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
      // Tính tổngAmount từ items
      this.orders = data.map(order => ({
        ...order,
        totalAmount: order.items
          ? order.items.reduce((sum, item) => sum + item.quantity * item.product.price, 0)
          : 0
      }));

      this.filteredOrders = [...this.orders];
      this.applyFilter();
    },
    error: (err) => console.error('Error fetching orders', err)
  });
}

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
  selectedDetailOrder: OrderDetail | null = null;
  purchasedItems: OrderItem[] = []; // sản phẩm đã mua

  openDetailModal(order: Order) {
  // Nếu order chưa có items, gán rỗng
  this.selectedDetailOrder = {
    ...order,
    items: (order as any).items || []
  };

  // Tính tổng tiền dựa trên price, quantity
  this.selectedDetailOrder.totalAmount = this.selectedDetailOrder.items
    .reduce((sum, item) => sum + item.quantity * item.product.price, 0);

  // Tổng hợp tất cả sản phẩm đã mua của khách hàng
  this.purchasedItems = this.orders
    .filter(o => o.customer.name === order.customer.name)
    .flatMap(o => (o as any).items || []);
}



  closeDetailModal() {
    this.selectedDetailOrder = null;
    this.purchasedItems = [];
  }
}
