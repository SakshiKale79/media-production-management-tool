import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class TeamMemberService {
  private baseUrl = 'http://localhost:8080/team-member';

  constructor(private http: HttpClient) {}

  getTasks(userId: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/tasks?userId=${userId}`);
  }

  updateTaskStatus(taskId: number, status: string): Observable<any> {
    return this.http.put(`${this.baseUrl}/tasks/${taskId}/status?status=${status}`, {});
  }
}