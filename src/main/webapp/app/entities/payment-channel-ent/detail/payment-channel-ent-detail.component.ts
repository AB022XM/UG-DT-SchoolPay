import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPaymentChannelEnt } from '../payment-channel-ent.model';

@Component({
  selector: 'jhi-payment-channel-ent-detail',
  templateUrl: './payment-channel-ent-detail.component.html',
})
export class PaymentChannelEntDetailComponent implements OnInit {
  paymentChannelEnt: IPaymentChannelEnt | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ paymentChannelEnt }) => {
      this.paymentChannelEnt = paymentChannelEnt;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
