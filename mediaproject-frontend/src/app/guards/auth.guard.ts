import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {

  constructor(private router: Router) {}

  canActivate(route: ActivatedRouteSnapshot): boolean {
    const user = sessionStorage.getItem('user');
    const expectedRole = route.data['role'];

    if (!user) {
      this.router.navigate(['/login']);
      return false;
    }

    const userData = JSON.parse(user);
    const userRole = userData.role;

    if (userRole === expectedRole) {
      return true;
    }

    // Redirect to their own dashboard if role doesn't match
    switch (userRole) {
      case 'ADMIN':
        this.router.navigate(['/admin/dashboard']);
        break;
      case 'MANAGER':
        this.router.navigate(['/manager/dashboard']);
        break;
      case 'TEAM_MEMBER':
        this.router.navigate(['/team/dashboard']);
        break;
    }
    return false;
  }
}
