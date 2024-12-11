import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [RouterLink, CommonModule],
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent {

  private cardColors: string[] = [
    '#fdffb6', // light yellow
    '#E0F7FA', // Light Cyan
  ];

  getCardColor(index: number): string {
    return this.cardColors[index % this.cardColors.length];
  }

}
