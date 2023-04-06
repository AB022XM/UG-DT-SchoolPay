import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { PaymentEntFormService, PaymentEntFormGroup } from './payment-ent-form.service';
import { IPaymentEnt } from '../payment-ent.model';
import { PaymentEntService } from '../service/payment-ent.service';
import { IBiller } from 'app/entities/biller/biller.model';
import { BillerService } from 'app/entities/biller/service/biller.service';
import { RecordStatus } from 'app/entities/enumerations/record-status.model';
import { PaymentChannel } from 'app/entities/enumerations/payment-channel.model';

@Component({
  selector: 'jhi-payment-ent-update',
  templateUrl: './payment-ent-update.component.html',
})
export class PaymentEntUpdateComponent implements OnInit {
  isSaving = false;
  paymentEnt: IPaymentEnt | null = null;
  recordStatusValues = Object.keys(RecordStatus);
  paymentChannelValues = Object.keys(PaymentChannel);

  billersSharedCollection: IBiller[] = [];

  editForm: PaymentEntFormGroup = this.paymentEntFormService.createPaymentEntFormGroup();

  constructor(
    protected paymentEntService: PaymentEntService,
    protected paymentEntFormService: PaymentEntFormService,
    protected billerService: BillerService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareBiller = (o1: IBiller | null, o2: IBiller | null): boolean => this.billerService.compareBiller(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ paymentEnt }) => {
      this.paymentEnt = paymentEnt;
      if (paymentEnt) {
        this.updateForm(paymentEnt);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const paymentEnt = this.paymentEntFormService.getPaymentEnt(this.editForm);
    if (paymentEnt.id !== null) {
      this.subscribeToSaveResponse(this.paymentEntService.update(paymentEnt));
    } else {
      this.subscribeToSaveResponse(this.paymentEntService.create(paymentEnt));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPaymentEnt>>): void {
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

  protected updateForm(paymentEnt: IPaymentEnt): void {
    this.paymentEnt = paymentEnt;
    this.paymentEntFormService.resetForm(this.editForm, paymentEnt);

    this.billersSharedCollection = this.billerService.addBillerToCollectionIfMissing<IBiller>(
      this.billersSharedCollection,
      paymentEnt.biller
    );
  }

  protected loadRelationshipsOptions(): void {
    this.billerService
      .query()
      .pipe(map((res: HttpResponse<IBiller[]>) => res.body ?? []))
      .pipe(map((billers: IBiller[]) => this.billerService.addBillerToCollectionIfMissing<IBiller>(billers, this.paymentEnt?.biller)))
      .subscribe((billers: IBiller[]) => (this.billersSharedCollection = billers));
  }
}
