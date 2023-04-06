import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../payment-channel-ent.test-samples';

import { PaymentChannelEntFormService } from './payment-channel-ent-form.service';

describe('PaymentChannelEnt Form Service', () => {
  let service: PaymentChannelEntFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PaymentChannelEntFormService);
  });

  describe('Service methods', () => {
    describe('createPaymentChannelEntFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createPaymentChannelEntFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            recordUniqueIdentifier: expect.any(Object),
            channelId: expect.any(Object),
            channelCode: expect.any(Object),
            channelName: expect.any(Object),
            status: expect.any(Object),
            freeField1: expect.any(Object),
            freeField2: expect.any(Object),
            freeField3: expect.any(Object),
            isDeleted: expect.any(Object),
          })
        );
      });

      it('passing IPaymentChannelEnt should create a new form with FormGroup', () => {
        const formGroup = service.createPaymentChannelEntFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            recordUniqueIdentifier: expect.any(Object),
            channelId: expect.any(Object),
            channelCode: expect.any(Object),
            channelName: expect.any(Object),
            status: expect.any(Object),
            freeField1: expect.any(Object),
            freeField2: expect.any(Object),
            freeField3: expect.any(Object),
            isDeleted: expect.any(Object),
          })
        );
      });
    });

    describe('getPaymentChannelEnt', () => {
      it('should return NewPaymentChannelEnt for default PaymentChannelEnt initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createPaymentChannelEntFormGroup(sampleWithNewData);

        const paymentChannelEnt = service.getPaymentChannelEnt(formGroup) as any;

        expect(paymentChannelEnt).toMatchObject(sampleWithNewData);
      });

      it('should return NewPaymentChannelEnt for empty PaymentChannelEnt initial value', () => {
        const formGroup = service.createPaymentChannelEntFormGroup();

        const paymentChannelEnt = service.getPaymentChannelEnt(formGroup) as any;

        expect(paymentChannelEnt).toMatchObject({});
      });

      it('should return IPaymentChannelEnt', () => {
        const formGroup = service.createPaymentChannelEntFormGroup(sampleWithRequiredData);

        const paymentChannelEnt = service.getPaymentChannelEnt(formGroup) as any;

        expect(paymentChannelEnt).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IPaymentChannelEnt should not enable id FormControl', () => {
        const formGroup = service.createPaymentChannelEntFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewPaymentChannelEnt should disable id FormControl', () => {
        const formGroup = service.createPaymentChannelEntFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
