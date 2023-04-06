import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { PaymentEntComponent } from '../list/payment-ent.component';
import { PaymentEntDetailComponent } from '../detail/payment-ent-detail.component';
import { PaymentEntUpdateComponent } from '../update/payment-ent-update.component';
import { PaymentEntRoutingResolveService } from './payment-ent-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const paymentEntRoute: Routes = [
  {
    path: '',
    component: PaymentEntComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PaymentEntDetailComponent,
    resolve: {
      paymentEnt: PaymentEntRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PaymentEntUpdateComponent,
    resolve: {
      paymentEnt: PaymentEntRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PaymentEntUpdateComponent,
    resolve: {
      paymentEnt: PaymentEntRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(paymentEntRoute)],
  exports: [RouterModule],
})
export class PaymentEntRoutingModule {}
