import { Component, ViewChild, AfterViewInit } from '@angular/core';
import { UserRecord } from '../../../../interfaces/dash-view.interface';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { CommonModule } from '@angular/common';
import { MatTableModule } from '@angular/material/table';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatSortModule } from '@angular/material/sort';
import { MatCardModule } from '@angular/material/card';

const USER_DATA: UserRecord[] = [
  { username: 'Tushar Nagdive', email: 'tushar@example.com', role: 'ADMIN', status: 'Active' },
  { username: 'John Doe', email: 'john@example.com', role: 'USER', status: 'Inactive' },
  { username: 'Jane Smith', email: 'jane@example.com', role: 'PAID_USER', status: 'Active' },
  { username: 'Guest User', email: 'guest@example.com', role: 'FREE_USER', status: 'Inactive' },
];

@Component({
  selector: 'app-users',
  standalone: true,
  imports: [
    CommonModule, 
    MatTableModule, 
    MatPaginatorModule, 
    MatSortModule, 
    MatCardModule
  ],
  templateUrl: './users.component.html',
  styleUrl: './users.component.scss'
})
export class UsersComponent implements AfterViewInit {
  displayedColumns: string[] = ['username', 'email', 'role', 'status'];
  dataSource = new MatTableDataSource(USER_DATA);

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
  }
}
