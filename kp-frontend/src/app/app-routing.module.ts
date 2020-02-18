import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {PaymentServicesComponent} from './component/payment-services/payment-services.component';
import {AddSellerServiceComponent} from './component/add-seller-service/add-seller-service.component';


const routes: Routes = [
  // { path: '', redirectTo: '/home', pathMatch: 'full'},
  { path: 'payment/:hashedId', component: PaymentServicesComponent},
  { path: 'new-seller/:id', component: AddSellerServiceComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
