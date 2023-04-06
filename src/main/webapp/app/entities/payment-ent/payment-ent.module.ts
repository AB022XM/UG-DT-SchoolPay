import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { PaymentEntComponent } from './list/payment-ent.component';
import { PaymentEntDetailComponent } from './detail/payment-ent-detail.component';
import { PaymentEntUpdateComponent } from './update/payment-ent-update.component';
import { PaymentEntDeleteDialogComponent } from './delete/payment-ent-delete-dialog.component';
import { PaymentEntRoutingModule } from './route/payment-ent-routing.module';

@NgModule({
  imports: [SharedModule, PaymentEntRoutingModule],
  declarations: [PaymentEntComponent, PaymentEntDetailComponent, PaymentEntUpdateComponent, PaymentEntDeleteDialogComponent],
})
export class PaymentEntModule {}
