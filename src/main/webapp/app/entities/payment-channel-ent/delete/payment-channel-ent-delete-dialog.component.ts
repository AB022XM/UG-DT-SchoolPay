import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IPaymentChannelEnt } from '../payment-channel-ent.model';
import { PaymentChannelEntService } from '../service/payment-channel-ent.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './payment-channel-ent-delete-dialog.component.html',
})
export class PaymentChannelEntDeleteDialogComponent {
  paymentChannelEnt?: IPaymentChannelEnt;

  constructor(protected paymentChannelEntService: PaymentChannelEntService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.paymentChannelEntService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
