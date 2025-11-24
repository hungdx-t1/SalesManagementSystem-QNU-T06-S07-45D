import { Component, OnInit } from '@angular/core';
import { CommonModule, NgFor } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { CustomerService } from '../../services/customers/customer';

@Component({
  selector: 'app-customer',
  standalone: true,
  imports: [CommonModule, NgFor, FormsModule],
  templateUrl: './customer.html',
  styleUrls: ['./customer.css']
})
export class Customer implements OnInit {

  customers: any[] = [];
  searchKeyword: string = '';
  newCustomer: any = { name: '', email: '', phone: '' };
  editingCustomer: any = null;

  constructor(private customerService: CustomerService) {}

  ngOnInit(): void {
    this.loadCustomers();
  }

  // ================== LOAD ==================
  loadCustomers() {
    this.customerService.getAll().subscribe({
      next: (data) => this.customers = data,
      error: (err) => console.error(err)
    });
  }

  // ================== SEARCH ==================
  search() {
  console.log("searching for:", this.searchKeyword);

  if (!this.searchKeyword.trim()) {
    this.loadCustomers();
    return;
  }

  this.customerService.search(this.searchKeyword)
    .subscribe({
      next: (data) => {
        console.log("search result:", data);
        this.customers = data;
      },
      error: (err) => console.error(err)
    });
}



  // ================== CREATE ==================
  createCustomer() {
    this.customerService.create(this.newCustomer).subscribe({
      next: () => {
        this.newCustomer = { name: '', email: '', phone: '' };
        this.loadCustomers();
      },
      error: (err) => console.error(err)
    });
  }

  // ================== DELETE ==================
  deleteCustomer(id: number) {
    if (!confirm('Bạn có chắc muốn xóa khách hàng này?')) return;

    this.customerService.delete(id).subscribe({
      next: () => this.loadCustomers(),
      error: (err) => console.error(err)
    });
  }

  // ================== EDIT ==================
  startEdit(customer: any) {
    this.editingCustomer = { ...customer };
  }

  saveEdit() {
    this.customerService.update(this.editingCustomer.id, this.editingCustomer).subscribe({
      next: () => {
        this.editingCustomer = null;
        this.loadCustomers();
      },
      error: (err) => console.error(err)
    });
  }

  cancelEdit() {
    this.editingCustomer = null;
  }
}
