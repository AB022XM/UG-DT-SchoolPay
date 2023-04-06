import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { PaymentChannelEntComponent } from '../list/payment-channel-ent.component';
import { PaymentChannelEntDetailComponent } from '../detail/payment-channel-ent-detail.component';
import { PaymentChannelEntUpdateComponent } from '../update/payment-channel-ent-update.component';
import { PaymentChannelEntRoutingResolveService } from './payment-channel-ent-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const paymentChannelEntRoute: Routes = [
  {
    path: '',
    component: PaymentChannelEntComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PaymentChannelEntDetailComponent,
    resolve: {
      paymentChannelEnt: PaymentChannelEntRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PaymentChannelEntUpdateComponent,
    resolve: {
      paymentChannelEnt: PaymentChannelEntRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PaymentChannelEntUpdateComponent,
    resolve: {
      paymentChannelEnt: PaymentChannelEntRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(paymentChannelEntRoute)],
  exports: [RouterModule],
})
export class PaymentChannelEntRoutingModule {}
