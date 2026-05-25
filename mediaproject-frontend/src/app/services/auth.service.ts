import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private baseUrl = 'http://localhost:8080/api/auth';  // Your backend base URL

  constructor(private http: HttpClient, private router: Router) {}

  // ✅ Signup API call
  signup(data: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/signup`, data);
  }

  // ✅ Login API call
  login(user: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/login`, user).pipe(
      tap((response: any) => {
        console.log("login response", response);
        // Store data in sessionStorage
        sessionStorage.setItem('role', response.role);
        sessionStorage.setItem('username', response.username);
        sessionStorage.setItem('userId', response.id); // 🔑 used in Team Dashboard

        // Redirect based on role
        if (response.role === 'ADMIN') {
          this.router.navigate(['/admin/dashboard']);
        } else if (response.role === 'MANAGER') {
          this.router.navigate(['/manager/dashboard']);
        } else if (response.role === 'TEAM_MEMBER') {
          this.router.navigate(['/team/dashboard']);
        } else {
          console.warn('Unknown role:', response.role);
        }
      })
    );
  }

  // ✅ Logout helper
  logout(): void {
    sessionStorage.clear();
    this.router.navigate(['/login']);
  }

  // ✅ Auth check (optional helper)
  isAuthenticated(): boolean {
    return sessionStorage.getItem('role') !== null;
  }

  // ✅ Get user role
  getRole(): string | null {
    return sessionStorage.getItem('role');
  }
}