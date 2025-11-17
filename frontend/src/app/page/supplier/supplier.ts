import { Component, OnInit, Injectable } from '@angular/core';
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
  standalone: true,
  imports: [CommonModule, FormsModule, HttpClientModule],
  templateUrl: './supplier.html',
  styleUrls: ['./supplier.css']
})
export class SupplierComponent implements OnInit{
  suppliers: Supplier[] = [];
  newSupplier: Supplier = { name: '' };


  constructor(private services: Services, private http: HttpClient) {}

  ngOnInit() {
    this.loadSuppliers();
  }

  loadSuppliers(): void {
  this.http.get<Supplier[]>(`${this.services.apiUrl}/api/suppliers`)
    .subscribe({
      next: data => this.suppliers = data,
      error: err => console.error('Lỗi khi load suppliers', err)
    });
}

  addSupplier(form: NgForm): void {
    if (!this.newSupplier.name) return; // validate đơn giản

    this.http.post<Supplier>(`${this.services.apiUrl}/api/suppliers`, this.newSupplier)
      .subscribe(() => {
        this.newSupplier = { name: '' }; // reset form
        form.resetForm();
        this.loadSuppliers(); // reload danh sách
      });
  }

  deleteSupplier(id: number): void {
    if (confirm('Bạn có chắc muốn xóa nhà cung cấp này không?')) {
      this.http.delete<void>(`${this.services.apiUrl}/api/suppliers/${id}`)
        .subscribe(() => this.loadSuppliers()); // reload danh sách
    }
  }
}
