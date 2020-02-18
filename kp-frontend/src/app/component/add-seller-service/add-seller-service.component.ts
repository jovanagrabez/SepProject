import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {PaymentService} from '../../service/payment.service';
import {ToastrManager} from 'ng6-toastr-notifications';
import {DOCUMENT} from '@angular/common';

@Component({
  selector: 'app-add-payment-service',
  templateUrl: './add-seller-service.component.html',
  styleUrls: ['./add-seller-service.component.css']
})
export class AddSellerServiceComponent implements OnInit {

  private id: string;
  private paymentMethods: any;
  constructor(private route: ActivatedRoute, private paymentService: PaymentService, private toastr: ToastrManager) { }

  ngOnInit() {
    this.id = this.route.snapshot.paramMap.get('id');
    this.paymentService.getPaymentMethodsForms().subscribe(ret => {
      this.paymentMethods = ret;
    });
  }

  submit() {

    this.paymentService.newPaymentMethods(this.id, this.paymentMethods).subscribe(ret => {
      this.toastr.successToastr('Success', 'Payment methods successfully updated');
      document.location.href = 'https://localhost:4200';
    }, err => {
      this.toastr.errorToastr('Error', 'Error while updating payment methods');
      document.location.href = 'https://localhost:4200';
    });
  }
}
