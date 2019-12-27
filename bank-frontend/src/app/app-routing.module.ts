import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {CardComponentComponent} from './component/home/card-component/card-component.component';



const routes: Routes = [
  {path: '', redirectTo: '/home', pathMatch: 'full'},
  {path: 'home/:id', component: CardComponentComponent}
  ];
@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
