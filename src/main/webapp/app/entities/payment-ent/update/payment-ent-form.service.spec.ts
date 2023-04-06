import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../payment-ent.test-samples';

import { PaymentEntFormService } from './payment-ent-form.service';

describe('PaymentEnt Form Service', () => {
  let service: PaymentEntFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PaymentEntFormService);
  });

  describe('Service methods', () => {
    describe('createPaymentEntFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createPaymentEntFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            recordUniqueIdentifier: expect.any(Object),
            paymentId: expect.any(Object),
            paymentCode: expect.any(Object),
            customerId: expect.any(Object),
            feeAmount: expect.any(Object),
            isAmountFixed: expect.any(Object),
            feeDescription: expect.any(Object),
            fixedAmount: expect.any(Object),
            paymentName: expect.any(Object),
            outstandingAmount: expect.any(Object),
            paymentChannel: expect.any(Object),
            freeField1: expect.any(Object),
            freeField2: expect.any(Object),
            freeField3: expect.any(Object),
            biller: expect.any(Object),
          })
        );
      });

      it('passing IPaymentEnt should create a new form with FormGroup', () => {
        const formGroup = service.createPaymentEntFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            recordUniqueIdentifier: expect.any(Object),
            paymentId: expect.any(Object),
            paymentCode: expect.any(Object),
            customerId: expect.any(Object),
            feeAmount: expect.any(Object),
            isAmountFixed: expect.any(Object),
            feeDescription: expect.any(Object),
            fixedAmount: expect.any(Object),
            paymentName: expect.any(Object),
            outstandingAmount: expect.any(Object),
            paymentChannel: expect.any(Object),
            freeField1: expect.any(Object),
            freeField2: expect.any(Object),
            freeField3: expect.any(Object),
            biller: expect.any(Object),
          })
        );
      });
    });

    describe('getPaymentEnt', () => {
      it('should return NewPaymentEnt for default PaymentEnt initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createPaymentEntFormGroup(sampleWithNewData);

        const paymentEnt = service.getPaymentEnt(formGroup) as any;

        expect(paymentEnt).toMatchObject(sampleWithNewData);
      });

      it('should return NewPaymentEnt for empty PaymentEnt initial value', () => {
        const formGroup = service.createPaymentEntFormGroup();

        const paymentEnt = service.getPaymentEnt(formGroup) as any;

        expect(paymentEnt).toMatchObject({});
      });

      it('should return IPaymentEnt', () => {
        const formGroup = service.createPaymentEntFormGroup(sampleWithRequiredData);

        const paymentEnt = service.getPaymentEnt(formGroup) as any;

        expect(paymentEnt).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IPaymentEnt should not enable id FormControl', () => {
        const formGroup = service.createPaymentEntFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewPaymentEnt should disable id FormControl', () => {
        const formGroup = service.createPaymentEntFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
