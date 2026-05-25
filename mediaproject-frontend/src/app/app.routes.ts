import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SignupComponent } from './components/signup/signup.component';
import { LoginComponent } from './components/login/login.component';
import { AdminComponent } from './dashboard/admin/admin.component';
import {ManagerComponent } from './dashboard/manager/manager.component';
import { TeamComponent } from './dashboard/team/team.component';
import { AuthGuard } from './guards/auth.guard';
import { HomeComponent } from './components/home/home.component';


export const routes: Routes = [
  
  { path: '', redirectTo: 'home', pathMatch: 'full' },
  {path:'home', component:HomeComponent},
  { path: 'login', component: LoginComponent },
  { path: 'signup', component: SignupComponent },
  
  // Role-based dashboards
  { path: 'admin/dashboard', component: AdminComponent, canActivate: [AuthGuard], data: { role: 'ADMIN' } },
  { path: 'manager/dashboard', component: ManagerComponent, canActivate: [AuthGuard], data: { role: 'MANAGER' } },
  { path: 'team/dashboard', component: TeamComponent, canActivate: [AuthGuard], data: { role: 'TEAM_MEMBER' } },

  { path: '**', redirectTo: 'login' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}
