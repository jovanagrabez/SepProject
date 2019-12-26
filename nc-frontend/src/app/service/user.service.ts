import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {map} from 'rxjs/operators';
import {Router} from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient, private router: Router) { }

  getToken(): string {
    const currentUser = JSON.parse(localStorage.getItem('loggedUser'));
    if (currentUser !== null) {
      currentUser.token = currentUser.token.replace(':', '');
    }
    const token = currentUser && currentUser.token;
    return token ? token : '';
  }

  login(user): any {
    return this.http.post('https://localhost:8762/naucna_centrala/api/auth/login', user,
      {observe: 'response'}).pipe(map(response => response));

  }

  isLoggedIn() {
    const user = JSON.parse(localStorage.getItem('loggedUser'));

    if (user === null) {
      return false;
    }
    const expiredDate = new Date(new Date(parseInt(user.exp, 10) * 1000));
    const nowDate = new Date();
    if ((expiredDate.getDate() <= nowDate.getDate()) &&
      (expiredDate.getTime() <= nowDate.getTime())) {
      this.logout();
      return false;
    }
    return true;

  }

  logout() {
    localStorage.clear();
    this.router.navigate(['/login']);
  }

  getUserByEmail(email: string) {
    return this.http.get('user-admin-service/api/users/'.concat(email));
  }

  getLoggedUserType() {
    const user = JSON.parse(localStorage.getItem('loggedUser'));
    let userRole;
    if (user === null) {
      userRole = '';
    } else {
      // for (const role of user.roles) {
      //   if (role === 'ROLE_SISTEM_ADMIN') {
      //     userRole = 'ROLE_SISTEM_ADMIN';
      //   }  else if (role === 'ROLE_ADMIN') {
      //     userRole = 'ROLE_ADMIN';
      //   } else {
      //     userRole = 'ROLE_USER';
      //   }
      // }
      userRole = 'ROLE_USER';
    }
    return userRole;
  }

  register(user): any {
    return this.http.post('user-admin-service/api/users/register', user);

  }
}
