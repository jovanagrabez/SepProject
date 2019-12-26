import { Component, OnInit } from '@angular/core';
import {MagazineService} from '../../service/magazine.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  magazinesList: any;

  constructor(private magazineService: MagazineService) { }

  ngOnInit() {
    this.magazineService.getAll().subscribe(magazines => {
      this.magazinesList = magazines;
    });
  }

  buyMagazine(magazineId: any) {
    this.magazineService.buyMagazine(magazineId).subscribe(result => {});
  }
}
