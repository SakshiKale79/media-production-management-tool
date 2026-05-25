import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
@Component({
  selector: 'app-home',
 standalone:true,
  imports:[CommonModule, FormsModule],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent {
  email: string ='';
  password : string='';
  constructor(private router: Router){}

  goToLogin(){
    this.router.navigateByUrl('/login');
  }

  goToSignup(){
    this.router.navigateByUrl('/signup');
  }
}
