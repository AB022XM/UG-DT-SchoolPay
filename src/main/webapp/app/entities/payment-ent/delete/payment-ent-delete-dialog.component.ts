import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IPaymentEnt } from '../payment-ent.model';
import { PaymentEntService } from '../service/payment-ent.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './payment-ent-delete-dialog.component.html',
})
export class PaymentEntDeleteDialogComponent {
  paymentEnt?: IPaymentEnt;

  constructor(protected paymentEntService: PaymentEntService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.paymentEntService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
