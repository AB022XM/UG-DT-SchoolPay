import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { PaymentChannelEntFormService, PaymentChannelEntFormGroup } from './payment-channel-ent-form.service';
import { IPaymentChannelEnt } from '../payment-channel-ent.model';
import { PaymentChannelEntService } from '../service/payment-channel-ent.service';
import { PaymentChannel } from 'app/entities/enumerations/payment-channel.model';
import { RecordStatus } from 'app/entities/enumerations/record-status.model';

@Component({
  selector: 'jhi-payment-channel-ent-update',
  templateUrl: './payment-channel-ent-update.component.html',
})
export class PaymentChannelEntUpdateComponent implements OnInit {
  isSaving = false;
  paymentChannelEnt: IPaymentChannelEnt | null = null;
  paymentChannelValues = Object.keys(PaymentChannel);
  recordStatusValues = Object.keys(RecordStatus);

  editForm: PaymentChannelEntFormGroup = this.paymentChannelEntFormService.createPaymentChannelEntFormGroup();

  constructor(
    protected paymentChannelEntService: PaymentChannelEntService,
    protected paymentChannelEntFormService: PaymentChannelEntFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ paymentChannelEnt }) => {
      this.paymentChannelEnt = paymentChannelEnt;
      if (paymentChannelEnt) {
        this.updateForm(paymentChannelEnt);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const paymentChannelEnt = this.paymentChannelEntFormService.getPaymentChannelEnt(this.editForm);
    if (paymentChannelEnt.id !== null) {
      this.subscribeToSaveResponse(this.paymentChannelEntService.update(paymentChannelEnt));
    } else {
      this.subscribeToSaveResponse(this.paymentChannelEntService.create(paymentChannelEnt));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPaymentChannelEnt>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(paymentChannelEnt: IPaymentChannelEnt): void {
    this.paymentChannelEnt = paymentChannelEnt;
    this.paymentChannelEntFormService.resetForm(this.editForm, paymentChannelEnt);
  }
}
