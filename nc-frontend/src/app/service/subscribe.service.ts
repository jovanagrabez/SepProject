import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {map} from 'rxjs/operators';
import {Router} from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class SubscribeService {

  constructor(private http: HttpClient, private router: Router) { }

  subscribe(magazineId): any{
    return this.http.get('https://localhost:8762/naucna_centrala/api/subscribe/'.concat(magazineId)).subscribe(url => {
        console.log(url["url"]);
        // @ts-ignore
        // this.router.navigate([url["url"]]);
        window.open(url["url"], "_blank");
        // this.document.location.href = url["url"];
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
