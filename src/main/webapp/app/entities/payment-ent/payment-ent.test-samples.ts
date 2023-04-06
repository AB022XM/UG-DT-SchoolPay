import { RecordStatus } from 'app/entities/enumerations/record-status.model';
import { PaymentChannel } from 'app/entities/enumerations/payment-channel.model';

import { IPaymentEnt, NewPaymentEnt } from './payment-ent.model';

export const sampleWithRequiredData: IPaymentEnt = {
  id: 13286,
  recordUniqueIdentifier: '604856d8-46cb-4987-ba98-7f525640cf83',
  paymentId: 35885,
  paymentCode: 41839,
  fixedAmount: 'Arizona Beauty',
  paymentName: 'context-sensitive Ergonomic',
  paymentChannel: PaymentChannel['ABSAINTERNETBANKING'],
};

export const sampleWithPartialData: IPaymentEnt = {
  id: 74279,
  recordUniqueIdentifier: '4a357d8b-4bb3-40b7-803c-24909d9792ad',
  paymentId: 53211,
  paymentCode: 42323,
  feeDescription: 'bus B2B',
  fixedAmount: 'Principal',
  paymentName: 'zero',
  outstandingAmount: 55561,
  paymentChannel: PaymentChannel['ABSAINTERNETBANKING'],
  freeField1: 'grid-enabled Kazakhstan Division',
  freeField2: 'tertiary Courts West',
};

export const sampleWithFullData: IPaymentEnt = {
  id: 17912,
  recordUniqueIdentifier: '89e53690-333b-49ee-8f7c-74269051e087',
  paymentId: 74052,
  paymentCode: 78081,
  customerId: 'Fantastic Aruba',
  feeAmount: 55615,
  isAmountFixed: RecordStatus['INACTIVE'],
  feeDescription: 'Salad Open-architected Buckinghamshire',
  fixedAmount: 'Vermont',
  paymentName: 'Fantastic',
  outstandingAmount: 23047,
  paymentChannel: PaymentChannel['USSD'],
  freeField1: 'Dam',
  freeField2: 'Portugal',
  freeField3: 'e-enable',
};

export const sampleWithNewData: NewPaymentEnt = {
  recordUniqueIdentifier: 'ad2ec874-7de6-43f7-aaf1-f5d0d4a3b621',
  paymentId: 44769,
  paymentCode: 68321,
  fixedAmount: 'withdrawal parsing Handcrafted',
  paymentName: 'Networked',
  paymentChannel: PaymentChannel['USSD'],
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
