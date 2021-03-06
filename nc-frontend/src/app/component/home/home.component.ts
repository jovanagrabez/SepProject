import { Component, OnInit } from '@angular/core';
import {MagazineService} from '../../service/magazine.service';
import {UserService} from '../../service/user.service';
import { SubscribeService } from 'src/app/service/subscribe.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  magazinesList: any;
  userLogged: boolean;
  currentMagazineId: any;
  insertTimeMode: boolean = false;
  subscriptionPeriod: number = 1;

  constructor(private magazineService: MagazineService, private userService: UserService,
    private subscribeService: SubscribeService) { }


  ngOnInit() {
    this.magazineService.getAll().subscribe(magazines => {
      this.magazinesList = magazines;
      this.userLogged = this.userService.isLoggedIn();
    });
  }

  buyMagazine(magazineId: any) {
    this.magazineService.buyMagazine(magazineId);
  }

  subscribeToMagazine() {
    this.subscribeService.subscribe(this.currentMagazineId, this.subscriptionPeriod);
    this.insertTimeMode = false;
  }

  addSubscriptionTime(magazineId: any) {
    this.currentMagazineId = magazineId;
    this.insertTimeMode = true;
  }

  cancel() {
    this.insertTimeMode = false;
  }
}
