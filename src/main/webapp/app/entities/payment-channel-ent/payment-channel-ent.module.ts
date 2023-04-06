import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { PaymentChannelEntComponent } from './list/payment-channel-ent.component';
import { PaymentChannelEntDetailComponent } from './detail/payment-channel-ent-detail.component';
import { PaymentChannelEntUpdateComponent } from './update/payment-channel-ent-update.component';
import { PaymentChannelEntDeleteDialogComponent } from './delete/payment-channel-ent-delete-dialog.component';
import { PaymentChannelEntRoutingModule } from './route/payment-channel-ent-routing.module';

@NgModule({
  imports: [SharedModule, PaymentChannelEntRoutingModule],
  declarations: [
    PaymentChannelEntComponent,
    PaymentChannelEntDetailComponent,
    PaymentChannelEntUpdateComponent,
    PaymentChannelEntDeleteDialogComponent,
  ],
})
export class PaymentChannelEntModule {}
