import { Component, OnInit } from '@angular/core';
import {Router} from '@angular/router';
import {UserService} from '../../service/user.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {

  userRole: any;
  user: any;
  constructor( private router: Router, private userService: UserService) { }


  ngOnInit() {
    this.userRole = this.userService.getLoggedUserType();
    const userTemp = JSON.parse(localStorage.getItem('loggedUser'));
    if (userTemp !== null) {
      this.userService.getUserByEmail(userTemp.sub).subscribe(user => {
        this.user = user;
      });
    }
  }

  logout() {
    localStorage.clear();
    this.userRole = '';
    this.router.navigate(['/login']);
  }

  registerPaymentMethods() {
    this.userService.registerPaymentMethodsForSeller();
  }
}
