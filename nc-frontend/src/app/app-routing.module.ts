import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {HomeComponent} from './component/home/home.component';
import {LogInComponent} from './component/users/log-in/log-in.component';
import {RegisterComponent} from './component/users/register/register.component';
import {AllPurchasesComponent} from './component/all-purchases/all-purchases.component';
import { CreateSubscriptionComponent } from './component/create-subscription/create-subscription.component';


const routes: Routes = [
  {path: '', redirectTo: '/home', pathMatch: 'full'},
  {path: 'home', component: HomeComponent},
  {path: 'login', component: LogInComponent},
  {path: 'register', component: RegisterComponent},
  {path: 'all-purchases', component: AllPurchasesComponent},
  {path: 'create-subscription', component: CreateSubscriptionComponent}
  ];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
