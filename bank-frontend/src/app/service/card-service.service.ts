import {Inject, Injectable} from '@angular/core';
import {Router} from '@angular/router';
import {HttpClient} from '@angular/common/http';
import {DOCUMENT} from "@angular/common";

@Injectable({
  providedIn: 'root'
})
export class CardServiceService {

  constructor(private http: HttpClient, private router: Router) { }

  submitData(cardData, bank) {
    return this.http.post('https://localhost:8762/'.concat(bank) + '/api/pay-by-card', cardData);
  }

  getData(id) {
    return this.http.get('https://localhost:8762/koncentrator_placanja/api/transaction/getTransaction/' + id);
  }

  getSeller(id) {
    return this.http.get('https://localhost:8762/koncentrator_placanja/api/transaction/getSeller/' + id);
  }
}
