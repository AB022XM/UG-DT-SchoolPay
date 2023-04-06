import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPaymentChannelEnt } from '../payment-channel-ent.model';
import { PaymentChannelEntService } from '../service/payment-channel-ent.service';

@Injectable({ providedIn: 'root' })
export class PaymentChannelEntRoutingResolveService implements Resolve<IPaymentChannelEnt | null> {
  constructor(protected service: PaymentChannelEntService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPaymentChannelEnt | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((paymentChannelEnt: HttpResponse<IPaymentChannelEnt>) => {
          if (paymentChannelEnt.body) {
            return of(paymentChannelEnt.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(null);
  }
}
