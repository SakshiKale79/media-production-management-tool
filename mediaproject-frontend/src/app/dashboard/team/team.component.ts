import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-team',
  standalone:true,
  imports :[CommonModule],
  templateUrl: './team.component.html',
  styleUrls: ['./team.component.css']
})
export class TeamComponent implements OnInit {
  tasks: any[] = [];
  userId: number = 0;

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    // ✅ Retrieve user from sessionStorage
    const user = JSON.parse(sessionStorage.getItem('user')|| '{}');
    
     
      this.userId = user.id; // 👈 set dynamic user ID
      this.getTasks();
    } 
  

  getTasks() {
    this.http.get<any[]>(`http://localhost:8080/team-member/tasks/${this.userId}`)
      .subscribe({
        next: (data) => this.tasks = data,
        error: (err) => console.error("Error loading tasks", err)
      });
  }

  updateStatus(taskId: number, status: string) {
    this.http.put(`http://localhost:8080/team-member/tasks/${taskId}/status?status=${status}`, {},{responseType:'text'})
      .subscribe({
        next: () => this.getTasks(),
        error: (err) => console.error("Status update failed", err)
      });
  }
}