import { Component } from '@angular/core';
import { ChartOptions, ChartType, ChartData } from 'chart.js';
import { BaseChartDirective } from 'ng2-charts';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-statistics',
  standalone: true,
  imports: [BaseChartDirective, CommonModule],
  templateUrl: './statistics.component.html',
  styleUrls: ['./statistics.component.scss']
})
export class StatisticsComponent {

  // Pie Chart - Invoice Status
  pieChartOptions: ChartOptions = {
    responsive: true,
    plugins: {
      legend: {
        position: 'bottom'
      }
    }
  };

  pieChartLabels = ['Paid', 'Unpaid', 'Overdue'];

  pieChartData: ChartData<'doughnut'> = {
    labels: this.pieChartLabels,
    datasets: [
      {
        data: [350, 100, 50],
        backgroundColor: ['#4caf50', '#ffc107', '#f44336']
      }
    ]
  };

  pieChartType: ChartType = 'doughnut';

  // Bar Chart - Reconciliation Trends
  barChartOptions: ChartOptions = {
    responsive: true,
    plugins: {
      legend: {
        position: 'top'
      }
    }
  };

  barChartLabels = ['Jan', 'Feb', 'Mar', 'Apr'];

  barChartData = [
    { data: [12, 19, 7, 5], label: 'Reconciliations' }
  ];

  barChartType: ChartType = 'bar';

}
