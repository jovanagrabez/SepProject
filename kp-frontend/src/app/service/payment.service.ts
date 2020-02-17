import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import has = Reflect.has;

@Injectable({
  providedIn: 'root'
})
export class PaymentService {

  constructor(private http: HttpClient) { }

  getAvailablePaymentMethods(hashedId: string) {
    return this.http.get('https://localhost:8762/koncentrator_placanja/api/transaction/pay_method/'.concat(hashedId));
  }

  getPaymentMethodsForms() {
    return this.http.get('https://localhost:8762/koncentrator_placanja/api/payment-methods');
  }

  newPaymentMethods(userId: string, formData: any) {
    return this.http.post('https://localhost:8762/koncentrator_placanja/api/seller/'.concat(userId), formData);
  }
}
