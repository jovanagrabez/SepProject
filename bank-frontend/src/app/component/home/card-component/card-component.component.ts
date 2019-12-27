import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {CardServiceService} from '../../../service/card-service.service';

@Component({
  selector: 'app-card-component',
  templateUrl: './card-component.component.html',
  styleUrls: ['./card-component.component.css']
})
export class CardComponentComponent implements OnInit {


  private Id;
  private data;
  private nesto;

  cardElements = {pan: '', securityCode: 0, cardHolderName: '', validTo: null, paymentId: 0,
    amount: 0,
    merchantOrderId: 0}
  constructor(private router: Router, private route: ActivatedRoute, private cardService: CardServiceService) { }

  ngOnInit() {
    this.Id = this.route.snapshot.paramMap.get('id');
    this.cardService.getData(this.Id).subscribe(res => {
      this.data = res;

    });
    console.log(this.data);
  }

  checkCard() {
    // @ts-ignore
    this.cardElements = {amount: this.data.amount , merchantOrderId: this.data.merchantOrderId, paymentId: this.data.paymentId};
    this.cardService.submitData(this.cardElements).subscribe(res => {
      this.nesto = res;

    });
  }
}
