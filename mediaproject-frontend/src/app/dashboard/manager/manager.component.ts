import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-manager',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './manager.component.html',
  styleUrls: ['./manager.component.css']
})
export class ManagerComponent implements OnInit {
  projects: any[] = [];
  tasks: any[] = [];
  users: any[] = [];
  teams: any[] = [];

  newProject = { title: '', description: '', deadline: '' };
  selectedProject: any = null;

  newTask = { title: '', description: '', deadline: '', projectId: '', assignedUserId: '', status: 'PENDING' };
  selectedTask: any = null;

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    this.fetchProjects();
    this.fetchUsers();
    this.fetchTasks();
    this.fetchTeams();
  }

  fetchProjects() {
    this.http.get<any[]>('http://localhost:8080/manager/projects').subscribe(data => this.projects = data);
  }

  fetchUsers() {
    this.http.get<any[]>('http://localhost:8080/users').subscribe(data => this.users = data);
  }

  fetchTasks() {
    this.http.get<any[]>('http://localhost:8080/manager/tasks').subscribe(data => this.tasks = data);
  }

  fetchTeams() {
    this.http.get<any[]>('http://localhost:8080/teams').subscribe(data => this.teams = data);
  }

  createProject() {
    this.http.post('http://localhost:8080/manager/projects/create', this.newProject).subscribe(() => {
      this.fetchProjects();
      this.newProject = { title: '', description: '', deadline: '' };
    });
  }

  createTask() {
    this.http.post('http://localhost:8080/manager/tasks/create', this.newTask).subscribe(() => {
      this.fetchTasks();
      this.newTask = { title: '', description: '', deadline: '', projectId: '', assignedUserId: '', status: 'PENDING' };
    });
  }

  updateTaskStatus(taskId: number, status: string) {
    this.http.put(`http://localhost:8080/manager/tasks/${taskId}/status?status=${status}`, null)
      .subscribe(() => this.fetchTasks());
  }

  assignTask(taskId: number, userId: number) {
    this.http.put(`http://localhost:8080/manager/tasks/${taskId}/assign/${userId}`, null)
      .subscribe(() => this.fetchTasks());
  }

  unassignTask(taskId: number) {
    this.http.put(`http://localhost:8080/manager/tasks/${taskId}/unassign`, null)
      .subscribe(() => this.fetchTasks());
  }

  updateTask(task: any) {
    this.http.put(`http://localhost:8080/manager/tasks/update/${task.id}`, task)
      .subscribe(() => this.fetchTasks());
  }
  onAssignTask(taskId:number, event: Event):void {
    const target = event.target as HTMLSelectElement;
    const userId= Number(target.value);
    this.assignTask(taskId, userId);
  }
}