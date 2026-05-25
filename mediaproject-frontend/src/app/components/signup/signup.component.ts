import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
@Component({
  selector: 'app-signup',
  standalone:true,
  imports:[CommonModule, FormsModule],
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent {
  user = {
    name: '',
    email: '',
    password: '',
    role: 'TEAM_MEMBER'
  };

  roles = ['ADMIN', 'MANAGER', 'TEAM_MEMBER'];
  message = '';
  ngONInit(): void{
    this.user={
      name:'',
      email:'',
      password:'',
      role:''
    };
    this.message='';
  }

  constructor(private http: HttpClient, private router: Router) {}

  signup() {
    this.http.post('http://localhost:8080/api/auth/signup', this.user)
      .subscribe({
        next: () => {
          this.message = 'Signup successful!';
          setTimeout(() => this.router.navigate(['/login']), 1500);
        },
        error: () => this.message = 'Signup failed. Try again.'
      });
  }
}