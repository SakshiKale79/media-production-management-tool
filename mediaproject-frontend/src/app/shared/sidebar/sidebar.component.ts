import { Component,  } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-sidebar',
  standalone:true,
  imports:[CommonModule],
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.css']
})
export class SidebarComponent  {
  role: string = '';

  ngOnInit(): void {
    const user = sessionStorage.getItem('user');
    if (user) {
      this.role = JSON.parse(user).role;
    }
  }
}