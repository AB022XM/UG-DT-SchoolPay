import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IPaymentChannelEnt, NewPaymentChannelEnt } from '../payment-channel-ent.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IPaymentChannelEnt for edit and NewPaymentChannelEntFormGroupInput for create.
 */
type PaymentChannelEntFormGroupInput = IPaymentChannelEnt | PartialWithRequiredKeyOf<NewPaymentChannelEnt>;

type PaymentChannelEntFormDefaults = Pick<NewPaymentChannelEnt, 'id'>;

type PaymentChannelEntFormGroupContent = {
  id: FormControl<IPaymentChannelEnt['id'] | NewPaymentChannelEnt['id']>;
  recordUniqueIdentifier: FormControl<IPaymentChannelEnt['recordUniqueIdentifier']>;
  channelId: FormControl<IPaymentChannelEnt['channelId']>;
  channelCode: FormControl<IPaymentChannelEnt['channelCode']>;
  channelName: FormControl<IPaymentChannelEnt['channelName']>;
  status: FormControl<IPaymentChannelEnt['status']>;
  freeField1: FormControl<IPaymentChannelEnt['freeField1']>;
  freeField2: FormControl<IPaymentChannelEnt['freeField2']>;
  freeField3: FormControl<IPaymentChannelEnt['freeField3']>;
  isDeleted: FormControl<IPaymentChannelEnt['isDeleted']>;
};

export type PaymentChannelEntFormGroup = FormGroup<PaymentChannelEntFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class PaymentChannelEntFormService {
  createPaymentChannelEntFormGroup(paymentChannelEnt: PaymentChannelEntFormGroupInput = { id: null }): PaymentChannelEntFormGroup {
    const paymentChannelEntRawValue = {
      ...this.getFormDefaults(),
      ...paymentChannelEnt,
    };
    return new FormGroup<PaymentChannelEntFormGroupContent>({
      id: new FormControl(
        { value: paymentChannelEntRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      recordUniqueIdentifier: new FormControl(paymentChannelEntRawValue.recordUniqueIdentifier, {
        validators: [Validators.required],
      }),
      channelId: new FormControl(paymentChannelEntRawValue.channelId, {
        validators: [Validators.required],
      }),
      channelCode: new FormControl(paymentChannelEntRawValue.channelCode),
      channelName: new FormControl(paymentChannelEntRawValue.channelName, {
        validators: [Validators.required],
      }),
      status: new FormControl(paymentChannelEntRawValue.status),
      freeField1: new FormControl(paymentChannelEntRawValue.freeField1),
      freeField2: new FormControl(paymentChannelEntRawValue.freeField2),
      freeField3: new FormControl(paymentChannelEntRawValue.freeField3),
      isDeleted: new FormControl(paymentChannelEntRawValue.isDeleted),
    });
  }

  getPaymentChannelEnt(form: PaymentChannelEntFormGroup): IPaymentChannelEnt | NewPaymentChannelEnt {
    return form.getRawValue() as IPaymentChannelEnt | NewPaymentChannelEnt;
  }

  resetForm(form: PaymentChannelEntFormGroup, paymentChannelEnt: PaymentChannelEntFormGroupInput): void {
    const paymentChannelEntRawValue = { ...this.getFormDefaults(), ...paymentChannelEnt };
    form.reset(
      {
        ...paymentChannelEntRawValue,
        id: { value: paymentChannelEntRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): PaymentChannelEntFormDefaults {
    return {
      id: null,
    };
  }
}
