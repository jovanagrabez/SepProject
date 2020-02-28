import {Inject, Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {map} from 'rxjs/operators';
import {Router} from '@angular/router';
import {DOCUMENT} from '@angular/common'
import {ToastrManager} from 'ng6-toastr-notifications';

@Injectable({
  providedIn: 'root'
})
export class SubscribeService {

  constructor(private http: HttpClient, private router: Router, @Inject(DOCUMENT)private document: Document, private toastr: ToastrManager) { }

  subscribe(magazineId, subscriptionPeriod): any{
    return this.http.get('https://localhost:8762/naucna_centrala/api/subscribe/'.concat(magazineId).concat('/').concat(subscriptionPeriod)).subscribe(url => {
        console.log(url["url"]);
        // @ts-ignore
        // this.router.navigate([url["url"]]);
        // window.open(url["url"], "_blank");
      this.toastr.successToastr('Waiting for PayPal', 'PayPal is processing your subscription');
        this.document.location.href = url["url"];
        // this.http.get(url['url']).subscribe(ret =>{
        //    console.log('success');
        //});
    }, error => {
      this.http.get(error).subscribe(value => {
        console.log('error');
      });
    });
  }
}
