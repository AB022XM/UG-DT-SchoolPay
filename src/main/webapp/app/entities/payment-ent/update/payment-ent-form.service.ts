import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IPaymentEnt, NewPaymentEnt } from '../payment-ent.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IPaymentEnt for edit and NewPaymentEntFormGroupInput for create.
 */
type PaymentEntFormGroupInput = IPaymentEnt | PartialWithRequiredKeyOf<NewPaymentEnt>;

type PaymentEntFormDefaults = Pick<NewPaymentEnt, 'id'>;

type PaymentEntFormGroupContent = {
  id: FormControl<IPaymentEnt['id'] | NewPaymentEnt['id']>;
  recordUniqueIdentifier: FormControl<IPaymentEnt['recordUniqueIdentifier']>;
  paymentId: FormControl<IPaymentEnt['paymentId']>;
  paymentCode: FormControl<IPaymentEnt['paymentCode']>;
  customerId: FormControl<IPaymentEnt['customerId']>;
  feeAmount: FormControl<IPaymentEnt['feeAmount']>;
  isAmountFixed: FormControl<IPaymentEnt['isAmountFixed']>;
  feeDescription: FormControl<IPaymentEnt['feeDescription']>;
  fixedAmount: FormControl<IPaymentEnt['fixedAmount']>;
  paymentName: FormControl<IPaymentEnt['paymentName']>;
  outstandingAmount: FormControl<IPaymentEnt['outstandingAmount']>;
  paymentChannel: FormControl<IPaymentEnt['paymentChannel']>;
  freeField1: FormControl<IPaymentEnt['freeField1']>;
  freeField2: FormControl<IPaymentEnt['freeField2']>;
  freeField3: FormControl<IPaymentEnt['freeField3']>;
  biller: FormControl<IPaymentEnt['biller']>;
};

export type PaymentEntFormGroup = FormGroup<PaymentEntFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class PaymentEntFormService {
  createPaymentEntFormGroup(paymentEnt: PaymentEntFormGroupInput = { id: null }): PaymentEntFormGroup {
    const paymentEntRawValue = {
      ...this.getFormDefaults(),
      ...paymentEnt,
    };
    return new FormGroup<PaymentEntFormGroupContent>({
      id: new FormControl(
        { value: paymentEntRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      recordUniqueIdentifier: new FormControl(paymentEntRawValue.recordUniqueIdentifier, {
        validators: [Validators.required],
      }),
      paymentId: new FormControl(paymentEntRawValue.paymentId, {
        validators: [Validators.required],
      }),
      paymentCode: new FormControl(paymentEntRawValue.paymentCode, {
        validators: [Validators.required],
      }),
      customerId: new FormControl(paymentEntRawValue.customerId),
      feeAmount: new FormControl(paymentEntRawValue.feeAmount),
      isAmountFixed: new FormControl(paymentEntRawValue.isAmountFixed),
      feeDescription: new FormControl(paymentEntRawValue.feeDescription, {
        validators: [Validators.minLength(3), Validators.maxLength(200)],
      }),
      fixedAmount: new FormControl(paymentEntRawValue.fixedAmount, {
        validators: [Validators.required, Validators.minLength(1), Validators.maxLength(50)],
      }),
      paymentName: new FormControl(paymentEntRawValue.paymentName, {
        validators: [Validators.required, Validators.minLength(3), Validators.maxLength(50)],
      }),
      outstandingAmount: new FormControl(paymentEntRawValue.outstandingAmount),
      paymentChannel: new FormControl(paymentEntRawValue.paymentChannel, {
        validators: [Validators.required],
      }),
      freeField1: new FormControl(paymentEntRawValue.freeField1),
      freeField2: new FormControl(paymentEntRawValue.freeField2),
      freeField3: new FormControl(paymentEntRawValue.freeField3),
      biller: new FormControl(paymentEntRawValue.biller),
    });
  }

  getPaymentEnt(form: PaymentEntFormGroup): IPaymentEnt | NewPaymentEnt {
    return form.getRawValue() as IPaymentEnt | NewPaymentEnt;
  }

  resetForm(form: PaymentEntFormGroup, paymentEnt: PaymentEntFormGroupInput): void {
    const paymentEntRawValue = { ...this.getFormDefaults(), ...paymentEnt };
    form.reset(
      {
        ...paymentEntRawValue,
        id: { value: paymentEntRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): PaymentEntFormDefaults {
    return {
      id: null,
    };
  }
}
