import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, ParamMap} from '@angular/router';
import {switchMap} from 'rxjs/operators';
import {PaymentService} from '../../service/payment.service';

@Component({
  selector: 'app-payment-services',
  templateUrl: './payment-services.component.html',
  styleUrls: ['./payment-services.component.css']
})
export class PaymentServicesComponent implements OnInit {

  private hashedId: string;
  private paymentServices: any;
  constructor(private route: ActivatedRoute, private paymentService: PaymentService) { }

  ngOnInit() {

    this.hashedId = this.route.snapshot.paramMap.get('hashedId');
    this.paymentService.getAvailablePaymentMethods(this.hashedId).subscribe(ret => {
      this.paymentServices = ret;
      console.log(ret);
    });

  }

}
