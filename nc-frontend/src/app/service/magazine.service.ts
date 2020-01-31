import {Inject, Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Router} from '@angular/router';
import {DOCUMENT} from '@angular/common';

@Injectable({
  providedIn: 'root'
})
export class MagazineService {

  constructor(private http: HttpClient, private router: Router, @Inject(DOCUMENT) private document: Document) { }

  getAll(): any {
    return this.http.get('https://localhost:8762/naucna_centrala/api/magazine');
  }

  buyMagazine(magazineId): any {
    this.http.get('https://localhost:8762/naucna_centrala/api/magazine/buy/'.concat(magazineId)).subscribe(url => {
      // @ts-ignore
      this.document.location.href = url['url'];
       this.http.get(url['url']).subscribe(ret =>{
         console.log('success');
       });
    }, error => {
      this.http.get(error).subscribe(value => {
        console.log('success');
      });
    });
  }

  getAllPurchases(): any {
    return this.http.get('https://localhost:8762/naucna_centrala/api/purchase');
  }
}
