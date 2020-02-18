import {Inject, Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {map} from 'rxjs/operators';
import {Router} from '@angular/router';
import {DOCUMENT} from '@angular/common';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient, private router: Router, @Inject(DOCUMENT) private document: Document) {
  }

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
    return this.http.get('https://localhost:8762/naucna_centrala/api/user/'.concat(email));
  }

  getLoggedUserType() {
    const user = JSON.parse(localStorage.getItem('loggedUser'));
    let userRole;
    if (user === null) {
      userRole = '';
    } else {
      for (const role of user.roles) {
        if (role === 'AUTHOR') {
          userRole = 'AUTHOR';
        } else if (role === 'REVIEWER') {
          userRole = 'REVIEWER';
        } else if (role === 'EDITOR') {
          userRole = 'EDITOR';
        } else {
          userRole = 'USER';
        }
      }
    }
    return userRole;
  }

  register(user): any {
    return this.http.post('user-admin-service/api/users/register', user);

  }

  registerPaymentMethodsForSeller() {

    return this.http.get('https://localhost:8762/naucna_centrala/api/seller', {
      headers: new HttpHeaders({
        Accept: 'text/html',
        'Content-Type': 'application/json'
      }),
      responseType: 'text'
    }).subscribe(url => {
      // @ts-ignore
      this.document.location.href = url;
    });
    // return this.http.get('https://localhost:8762/koncentrator_placanja/api/seller/1', {
    //   headers: new HttpHeaders({
    //     Accept: 'text/html',
    //     'Content-Type': 'application/json'
    //   }),
    //   responseType: 'text'
    // });
  }
}


