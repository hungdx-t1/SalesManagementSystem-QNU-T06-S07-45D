import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { CommonModule, NgFor } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Services } from '../../services';

interface Category { id: number; name: string; }
interface Product { id: number; name: string; category: Category; price: number; stock: number; }
interface Supplier { id: number; name: string; }
interface ImportOrder { supplier: Supplier; quantity: number; total_cost: number; import_date: string; }

@Component({
  selector: 'app-warehouse',
  standalone: true,
  imports: [CommonModule, NgFor, FormsModule, HttpClientModule],
  templateUrl: './warehouse.html',
  styleUrl: './warehouse.css'
})
export class Warehouse implements OnInit {
  products: Product[] = [];
  filteredProducts: Product[] = [];
  suppliers: Supplier[] = [];
  searchTerm: string = '';

  importProduct: Product | null = null;
  selectedSupplier: Supplier | null = null;
  importQuantity: number = 0;
  importCost: number = 0;

  historyProduct: Product | null = null;
  stockHistory: ImportOrder[] = [];

  constructor(private http: HttpClient, private services: Services) {}

  ngOnInit() {
    this.loadProducts();
    this.loadSuppliers();
  }

  loadProducts() {
    const token = localStorage.getItem('jwt');
    this.http.get<Product[]>(`${this.services.apiUrl}/api/products`, { headers: { Authorization: `Bearer ${token}` } })
      .subscribe(data => {
        this.products = data;
        this.filteredProducts = [...this.products];
      });
  }

  loadSuppliers() {
    const token = localStorage.getItem('jwt');
    this.http.get<Supplier[]>(`${this.services.apiUrl}/api/suppliers`, { headers: { Authorization: `Bearer ${token}` } })
      .subscribe(data => this.suppliers = data);
  }

  search() {
    const term = this.searchTerm.toLowerCase();
    this.filteredProducts = this.products.filter(p => p.name.toLowerCase().includes(term));
  }

  // Nhập kho
  openImportModal(product: Product) {
    this.importProduct = product;
    this.selectedSupplier = this.suppliers[0] || null;
    this.importQuantity = 0;
    this.importCost = 0;
  }

  closeImportModal() { this.importProduct = null; }

  confirmImport() {
    if (!this.importProduct || !this.selectedSupplier || this.importQuantity <= 0) return;

    const payload = {
      product_id: this.importProduct.id,
      supplier_id: this.selectedSupplier.id,
      quantity: this.importQuantity,
      total_cost: this.importCost
    };

    const token = localStorage.getItem('jwt');
    this.http.post(`${this.services.apiUrl}/api/import-orders`, payload, { headers: { Authorization: `Bearer ${token}` } })
      .subscribe(() => {
        this.loadProducts(); // cập nhật stock
        this.closeImportModal();
      });
  }

  // Xem lịch sử nhập
  openStockHistoryModal(product: Product) {
    this.historyProduct = product;
    const token = localStorage.getItem('jwt');
    this.http.get<ImportOrder[]>(`${this.services.apiUrl}/api/import-orders?product_id=${product.id}`, { headers: { Authorization: `Bearer ${token}` } })
      .subscribe(data => this.stockHistory = data);
  }

  closeHistoryModal() { this.historyProduct = null; this.stockHistory = []; }
}
