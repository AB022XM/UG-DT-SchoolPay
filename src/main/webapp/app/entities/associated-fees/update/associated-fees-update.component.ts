import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { AssociatedFeesFormService, AssociatedFeesFormGroup } from './associated-fees-form.service';
import { IAssociatedFees } from '../associated-fees.model';
import { AssociatedFeesService } from '../service/associated-fees.service';
import { IPaymentEnt } from 'app/entities/payment-ent/payment-ent.model';
import { PaymentEntService } from 'app/entities/payment-ent/service/payment-ent.service';

@Component({
  selector: 'jhi-associated-fees-update',
  templateUrl: './associated-fees-update.component.html',
})
export class AssociatedFeesUpdateComponent implements OnInit {
  isSaving = false;
  associatedFees: IAssociatedFees | null = null;

  paymentEntsSharedCollection: IPaymentEnt[] = [];

  editForm: AssociatedFeesFormGroup = this.associatedFeesFormService.createAssociatedFeesFormGroup();

  constructor(
    protected associatedFeesService: AssociatedFeesService,
    protected associatedFeesFormService: AssociatedFeesFormService,
    protected paymentEntService: PaymentEntService,
    protected activatedRoute: ActivatedRoute
  ) {}

  comparePaymentEnt = (o1: IPaymentEnt | null, o2: IPaymentEnt | null): boolean => this.paymentEntService.comparePaymentEnt(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ associatedFees }) => {
      this.associatedFees = associatedFees;
      if (associatedFees) {
        this.updateForm(associatedFees);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const associatedFees = this.associatedFeesFormService.getAssociatedFees(this.editForm);
    if (associatedFees.id !== null) {
      this.subscribeToSaveResponse(this.associatedFeesService.update(associatedFees));
    } else {
      this.subscribeToSaveResponse(this.associatedFeesService.create(associatedFees));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAssociatedFees>>): void {
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

  protected updateForm(associatedFees: IAssociatedFees): void {
    this.associatedFees = associatedFees;
    this.associatedFeesFormService.resetForm(this.editForm, associatedFees);

    this.paymentEntsSharedCollection = this.paymentEntService.addPaymentEntToCollectionIfMissing<IPaymentEnt>(
      this.paymentEntsSharedCollection,
      associatedFees.paymentEnt
    );
  }

  protected loadRelationshipsOptions(): void {
    this.paymentEntService
      .query()
      .pipe(map((res: HttpResponse<IPaymentEnt[]>) => res.body ?? []))
      .pipe(
        map((paymentEnts: IPaymentEnt[]) =>
          this.paymentEntService.addPaymentEntToCollectionIfMissing<IPaymentEnt>(paymentEnts, this.associatedFees?.paymentEnt)
        )
      )
      .subscribe((paymentEnts: IPaymentEnt[]) => (this.paymentEntsSharedCollection = paymentEnts));
  }
}
