import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { NavbarComponent } from '../../shared/navbar/navbar.component';
import { SidebarComponent } from '../../shared/sidebar/sidebar.component';

@Component({
  selector: 'app-admin',
  standalone: true,
  imports: [CommonModule, FormsModule, NavbarComponent, SidebarComponent],
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent implements OnInit {
  users: any[] = [];
  roles: string[] = ['ADMIN', 'MANAGER', 'TEAM_MEMBER'];

  newUser = {
    username: '',
    email: '',
    password: '',
    role: 'TEAM_MEMBER'
  };

  // === PROJECTS ===
  projects: any[] = [];
  newProject = { title: '', description: '', deadline: '', managerId: '' };
  selectedProject: any = null;
  managers: any[] = [];

  // === TEAMS ===
  teams: any[] = [];
  selectedTeam: any = null;
  newTeam = { name: '', memberIds: [] as number[] };

  // === TASKS ===
  tasks: any[] = [];
  selectedTask: any = null;
  newTask = { title: '', description: '', deadline: '', projectId: '', assignedUserId: '', status: '' };

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    this.fetchUsers();
    this.fetchProjects();
    this.fetchTeams();
    this.fetchTasks();
  }

  // === USER METHODS ===
  fetchUsers(): void {
    this.http.get<any[]>('http://localhost:8080/users').subscribe({
      next: (data) => {
        this.users = data;
        this.fetchManagers(); // Fetch after users load
      },
      error: (err) => console.error('Error fetching users:', err)
    });
  }

  createUser(): void {
    this.http.post('http://localhost:8080/users/create', this.newUser).subscribe({
      next: () => {
        this.fetchUsers();
        this.newUser = { username: '', email: '', password: '', role: 'TEAM_MEMBER' };
      },
      error: (err) => console.error('Error creating user:', err)
    });
  }

  updateUser(user: any): void {
    this.http.put(`http://localhost:8080/users/update/${user.id}`, user).subscribe({
      next: () => this.fetchUsers(),
      error: (err) => console.error('Error updating user:', err)
    });
  }

  changeRole(userId: number, role: string): void {
    this.http.put(`http://localhost:8080/users/${userId}/role`, role, {
      headers: { 'Content-Type': 'application/json' }
    }).subscribe({
      next: () => this.fetchUsers(),
      error: (err) => console.error('Error updating role:', err)
    });
  }

  deleteUser(userId: number): void {
    this.http.delete(`http://localhost:8080/users/delete/${userId}`).subscribe({
      next: () => this.fetchUsers(),
      error: (err) => console.error('Error deleting user:', err)
    });
  }

  // === PROJECT METHODS ===
  fetchProjects(): void {
    this.http.get<any[]>('http://localhost:8080/projects').subscribe(data => {
      this.projects = data;
    });
  }

  fetchManagers(): void {
    this.managers = this.users.filter(user => user.role === 'MANAGER');
  }

  createProject(): void {
    this.http.post('http://localhost:8080/projects/create', this.newProject).subscribe(() => {
      this.fetchProjects();
      this.newProject = { title: '', description: '', deadline: '', managerId: '' };
    });
  }

  editProject(project: any): void {
    this.selectedProject = { ...project };
  }

  updateProject(): void {
    this.http.put(`http://localhost:8080/projects/update/${this.selectedProject.id}`, this.selectedProject).subscribe(() => {
      this.selectedProject = null;
      this.fetchProjects();
    });
  }

  cancelEditProject(): void {
    this.selectedProject = null;
  }

  // === TEAM METHODS ===
  fetchTeams(): void {
    this.http.get<any[]>('http://localhost:8080/teams').subscribe(data => this.teams = data);
  }

  createTeam(): void {
    this.http.post('http://localhost:8080/teams/create', this.newTeam).subscribe(() => {
      this.newTeam = { name: '', memberIds: [] };
      this.fetchTeams();
    });
  }

  updateTeam(): void {
    this.http.put(`http://localhost:8080/teams/update/${this.selectedTeam.id}`, this.selectedTeam).subscribe(() => {
      this.selectedTeam = null;
      this.fetchTeams();
    });
  }

  viewTeamById(id: number): void {
    this.http.get<any>(`http://localhost:8080/teams/${id}`).subscribe(team => {
      this.selectedTeam = team;
    });
  }

  viewTeamMembers(teamId: number): void {
    this.http.get<any[]>(`http://localhost:8080/teams/${teamId}/members`).subscribe(members => {
      if (this.selectedTeam) {
        this.selectedTeam.members = members;
      }
    });
  }

  onCheckboxChange(event: Event, userId: number): void {
    const input = event.target as HTMLInputElement;
    if (input.checked) {
      if (!this.newTeam.memberIds.includes(userId)) {
        this.newTeam.memberIds.push(userId);
      }
    } else {
      const index = this.newTeam.memberIds.indexOf(userId);
      if (index !== -1) {
        this.newTeam.memberIds.splice(index, 1);
      }
    }
  }

  // === TASK METHODS ===
  fetchTasks(): void {
    this.http.get<any[]>('http://localhost:8080/admin/tasks').subscribe(data => this.tasks = data);
  }

  createTask(): void {
    console.log("Submitted task:", this.newTask);
    if(!this.newTask.title || !this.newTask.description || !!this.newTask.deadline || !!this.newTask.projectId 
      || !!this.newTask.assignedUserId || !!this.newTask.status){
        alert("please fill all task fields.");
        return;
      }
    this.http.post('http://localhost:8080/admin/tasks/create', this.newTask).subscribe({
      next: ()=> {
        this.newTask ={title: '', description:'', deadline:'', projectId:'', assignedUserId:'', status:''};
        this.fetchTasks();
      },
      error:(err)=>{
        console.error('Error creating task:', err);
        alert("Task creation failed. ")
      }
    });
  }

  getTaskById(id: number): void {
    this.http.get<any>(`http://localhost:8080/admin/tasks/${id}`).subscribe(task => {
      this.selectedTask = task;
    });
  }

  editTask(task: any): void {
    this.selectedTask = { ...task };
  }

  updateTask(): void {
    this.http.put(`http://localhost:8080/admin/tasks/update/${this.selectedTask.id}`, this.selectedTask).subscribe(() => {
      this.selectedTask = null;
      this.fetchTasks();
    });
  }

  deleteTask(id: number): void {
    this.http.delete(`http://localhost:8080/admin/tasks/delete/${id}`).subscribe(() => this.fetchTasks());
  }

  updateTaskStatus(taskId: number, status: string): void {
    this.http.put(`http://localhost:8080/admin/tasks/${taskId}/status`, null, {
      params: { status }
    }).subscribe(() => this.fetchTasks());
  }

  cancelEditTask(): void {
    this.selectedTask = null;
  }
}