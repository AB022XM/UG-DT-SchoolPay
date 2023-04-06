import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPaymentEnt } from '../payment-ent.model';
import { PaymentEntService } from '../service/payment-ent.service';

@Injectable({ providedIn: 'root' })
export class PaymentEntRoutingResolveService implements Resolve<IPaymentEnt | null> {
  constructor(protected service: PaymentEntService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPaymentEnt | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((paymentEnt: HttpResponse<IPaymentEnt>) => {
          if (paymentEnt.body) {
            return of(paymentEnt.body);
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
