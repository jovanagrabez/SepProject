import {Component, Inject, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {CardServiceService} from '../../../service/card-service.service';
import {DOCUMENT} from '@angular/common';
import {ToastrManager} from 'ng6-toastr-notifications';

@Component({
  selector: 'app-card-component',
  templateUrl: './card-component.component.html',
  styleUrls: ['./card-component.component.css']
})
export class CardComponentComponent implements OnInit {


  private Id;
  private bank;
  private data;
  private nesto;
  private seller;
  cardElements = {sellerId: 0, pan: '', securityCode: 0, cardHolderName: '', validTo: null, paymentId: 0,
    amount: 0,
    merchantOrderId: 0, hashedId: 0, merchantId: 0};
  constructor(private router: Router, private route: ActivatedRoute, private cardService: CardServiceService,
              @Inject(DOCUMENT) private document: Document, private toastr: ToastrManager) { }

  ngOnInit() {
    this.Id = this.route.snapshot.paramMap.get('id');
    this.bank = this.route.snapshot.paramMap.get('bank');
    this.cardService.getData(this.Id).subscribe(res => {
      this.data = res;
      this.cardService.getSeller(this.Id).subscribe( result  => {
        this.seller = result;
      });

    });
    console.log(this.data);
  }

  checkCard() {
    // @ts-ignore
    this.cardElements.amount = this.data.amount;
    this.cardElements.merchantOrderId = this.data.merchantOrderId;
    this.cardElements.paymentId = this.data.paymentId;
    this.cardElements.hashedId = this.Id;
    this.cardElements.sellerId = this.data.sellerId;
    this.cardElements.merchantId = this.seller.merchantId;

    this.cardService.submitData(this.cardElements, this.bank).subscribe(res => {
      if (res['status'] === 'SUCCESS') {
      this.toastr.successToastr('Successful transaction!');
      } else {
        this.toastr.warningToastr('Error in transaction!');
      }
      this.nesto = res;
      // @ts-ignore
      this.document.location.href = res.url;

    });
  }
}
