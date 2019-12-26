import { Injectable } from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot} from '@angular/router';
import {UserService} from '../user.service';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CanActivateService implements CanActivate {

  constructor(private userService: UserService, private router: Router) { }

  canActivate(route: ActivatedRouteSnapshot,
              state: RouterStateSnapshot): Observable<boolean> | Promise<boolean> | boolean {
    if (this.userService.isLoggedIn()) {
      return true;
    }
    this.router.navigate(['/home']);
    return false;

  }
}
