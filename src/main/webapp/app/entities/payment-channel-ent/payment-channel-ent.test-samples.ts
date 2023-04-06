import { PaymentChannel } from 'app/entities/enumerations/payment-channel.model';
import { RecordStatus } from 'app/entities/enumerations/record-status.model';

import { IPaymentChannelEnt, NewPaymentChannelEnt } from './payment-channel-ent.model';

export const sampleWithRequiredData: IPaymentChannelEnt = {
  id: 83345,
  recordUniqueIdentifier: 'a6470b53-cd69-414a-87ee-2ede3b4b0a28',
  channelId: 64448,
  channelName: PaymentChannel['USSD'],
};

export const sampleWithPartialData: IPaymentChannelEnt = {
  id: 27953,
  recordUniqueIdentifier: '20ee0755-70ee-4c95-9fe8-81457e4ff93e',
  channelId: 9694,
  channelName: PaymentChannel['OVERTHECOUNTER'],
  status: RecordStatus['INACTIVE'],
  freeField2: 'wireless',
};

export const sampleWithFullData: IPaymentChannelEnt = {
  id: 33431,
  recordUniqueIdentifier: '6bf5c97c-8d50-4d24-8030-9fa966554759',
  channelId: 60499,
  channelCode: 65162,
  channelName: PaymentChannel['CHATBOT'],
  status: RecordStatus['INACTIVE'],
  freeField1: 'Swaziland Pants Bulgarian',
  freeField2: 'hacking HTTP markets',
  freeField3: 'users Central Forward',
  isDeleted: RecordStatus['INACTIVE'],
};

export const sampleWithNewData: NewPaymentChannelEnt = {
  recordUniqueIdentifier: '12d30a51-f9a5-48e2-8149-d02041c9a93a',
  channelId: 43433,
  channelName: PaymentChannel['CHATBOT'],
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
