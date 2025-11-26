import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, NgForm } from '@angular/forms';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { Services } from '../../services';

export interface Supplier {
  id?: number;
  name: string;
  email?: string;
  phone?: string;
  address?: string;
}

@Component({
  selector: 'app-supplier',
  imports: [CommonModule, FormsModule, HttpClientModule],
  templateUrl: './supplier.html',
  styleUrl: './supplier.css'
})
export class SupplierComponent {
  suppliers: Supplier[] = [];
  newSupplier: Supplier = { name: '' };

  constructor(private services: Services, private http: HttpClient) {}

  ngOnInit(): void {
    this.loadSuppliers();
  }

  // Lấy danh sách nhà cung cấp
  loadSuppliers(): void {
    const token = localStorage.getItem('jwt');
    this.http.get<Supplier[]>(`${this.services.apiUrl}/api/suppliers`)
      .subscribe({
        next: data => this.suppliers = data,
        error: err => console.error('Lỗi khi load suppliers', err)
      });
  }

  // Thêm nhà cung cấp
  addSupplier(form: NgForm): void {
    if (!this.newSupplier.name) return;

    this.http.post<Supplier>(`${this.services.apiUrl}/api/suppliers`, this.newSupplier)
      .subscribe({
        next: () => {
          this.newSupplier = { name: '' };
          form.resetForm();
          this.loadSuppliers();
        },
        error: err => console.error('Lỗi khi thêm supplier', err)
      });
  }

  // Xóa nhà cung cấp
  deleteSupplier(id: number): void {
    if (!confirm('Bạn có chắc muốn xóa nhà cung cấp này không?')) return;

    this.http.delete<void>(`${this.services.apiUrl}/api/suppliers/${id}`)
      .subscribe({
        next: () => this.loadSuppliers(),
        error: err => console.error('Lỗi khi xóa supplier', err)
      });
  }
}
