import { Component,ViewChild, AfterViewInit} from '@angular/core';
import { Invoice } from '../../../../interfaces/dash-view.interface';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { CommonModule } from '@angular/common';
import { MatTableModule } from '@angular/material/table';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatSortModule } from '@angular/material/sort';
import { MatCardModule } from '@angular/material/card';

const INVOICE_DATA: Invoice[] = [
  { invoiceId: 'INV-001', customerName: 'Tushar Nagdive', amount: 1200, status: 'Paid', issueDate: '2025-04-20' },
  { invoiceId: 'INV-002', customerName: 'John Doe', amount: 800, status: 'Unpaid', issueDate: '2025-04-22' },
  { invoiceId: 'INV-003', customerName: 'Jane Smith', amount: 500, status: 'Overdue', issueDate: '2025-03-15' },
  { invoiceId: 'INV-004', customerName: 'Company ABC', amount: 2500, status: 'Paid', issueDate: '2025-04-01' },
];

@Component({
  selector: 'app-invoices',
  standalone: true,
  imports: [
    CommonModule, 
    MatTableModule, 
    MatPaginatorModule, 
    MatSortModule, 
    MatCardModule
  ],
  templateUrl: './invoices.component.html',
  styleUrl: './invoices.component.scss'
})
export class InvoicesComponent implements AfterViewInit{
  displayedColumns: string[] = ['invoiceId', 'customerName', 'amount', 'status', 'issueDate'];
  dataSource = new MatTableDataSource(INVOICE_DATA);

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
  }
}
