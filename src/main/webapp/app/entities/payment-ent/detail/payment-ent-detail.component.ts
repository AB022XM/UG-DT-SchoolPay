import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPaymentEnt } from '../payment-ent.model';

@Component({
  selector: 'jhi-payment-ent-detail',
  templateUrl: './payment-ent-detail.component.html',
})
export class PaymentEntDetailComponent implements OnInit {
  paymentEnt: IPaymentEnt | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ paymentEnt }) => {
      this.paymentEnt = paymentEnt;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
