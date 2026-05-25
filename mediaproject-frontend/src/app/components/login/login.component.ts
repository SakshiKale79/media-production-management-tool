import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  credentials = {
    email: '',
    password: ''
  };
  errorMessage = '';

  constructor(private authService: AuthService, private router: Router) {}

  login() {
   console.log('login button clicked');
    if (!this.credentials.email || !this.credentials.password) {
      this.errorMessage = 'Please enter both email and password';
      return;
    }

    this.authService.login(this.credentials).subscribe({
      next: (result: any) => {
        sessionStorage.setItem('user', JSON.stringify({
          id: result.id,
          email: result.email,
          role: result.role,
          username: result.username
        }));
        switch (result.role) {
          case 'ADMIN':
            this.router.navigate(['/admin/dashboard']);
            break;
          case 'MANAGER':
            this.router.navigate(['/manager/dashboard']);
            break;
          case 'TEAM_MEMBER':
            this.router.navigate(['/team/dashboard']);
            break;
          default:
            this.router.navigate(['/login']);
            break;
        }
      },
      error: (err) => {
        this.errorMessage = 'Invalid credentials or server error';
      }
    });
  }
}