import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Router} from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class MagazineService {

  constructor(private http: HttpClient, private router: Router) { }

  getAll(): any {
    return this.http.get('https://localhost:8762/naucna_centrala/api/magazine');
  }

  buyMagazine(magazineId): any {
    return this.http.get('https://localhost:8762/naucna_centrala/api/magazine/buy/'.concat(magazineId));
}
}
