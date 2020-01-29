import { Component, OnInit } from '@angular/core';
import {MagazineService} from '../../service/magazine.service';

@Component({
  selector: 'app-all-purchases',
  templateUrl: './all-purchases.component.html',
  styleUrls: ['./all-purchases.component.css']
})
export class AllPurchasesComponent implements OnInit {

  private magazinePurchases: [];
  constructor(private magazineService: MagazineService) { }
  ngOnInit() {

    this.magazineService.getAllPurchases().subscribe(val => {
      this.magazinePurchases = val;
      console.log(val);
    });
  }

}
