import { Component, ViewChild, AfterViewInit } from '@angular/core';
import { ReconciliationRecord } from '../../../../interfaces/dash-view.interface';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { CommonModule } from '@angular/common';
import { MatTableModule } from '@angular/material/table';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatSortModule } from '@angular/material/sort';
import { MatCardModule } from '@angular/material/card';

const RECONCILIATION_DATA: ReconciliationRecord[] = [
  { reconciliationId: 'REC-001', invoiceId: 'INV-001', status: 'Matched', matchedAmount: 1200, reconciliationDate: '2025-04-22' },
  { reconciliationId: 'REC-002', invoiceId: 'INV-002', status: 'Unmatched', matchedAmount: 0, reconciliationDate: '2025-04-23' },
  { reconciliationId: 'REC-003', invoiceId: 'INV-003', status: 'Matched', matchedAmount: 500, reconciliationDate: '2025-04-24' },
  { reconciliationId: 'REC-004', invoiceId: 'INV-004', status: 'Matched', matchedAmount: 2500, reconciliationDate: '2025-04-25' },
];

@Component({
  selector: 'app-reconciliation',
  standalone: true,
  imports: [
    CommonModule, 
    MatTableModule, 
    MatPaginatorModule, 
    MatSortModule, 
    MatCardModule
  ],
  templateUrl: './reconciliation.component.html',
  styleUrl: './reconciliation.component.scss'
})
export class ReconciliationComponent implements AfterViewInit{

  displayedColumns: string[] = ['reconciliationId', 'invoiceId', 'status', 'matchedAmount', 'reconciliationDate'];
  dataSource = new MatTableDataSource(RECONCILIATION_DATA);

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
  }

}
