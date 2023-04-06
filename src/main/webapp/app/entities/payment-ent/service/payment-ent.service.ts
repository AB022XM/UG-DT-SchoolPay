import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPaymentEnt, NewPaymentEnt } from '../payment-ent.model';

export type PartialUpdatePaymentEnt = Partial<IPaymentEnt> & Pick<IPaymentEnt, 'id'>;

export type EntityResponseType = HttpResponse<IPaymentEnt>;
export type EntityArrayResponseType = HttpResponse<IPaymentEnt[]>;

@Injectable({ providedIn: 'root' })
export class PaymentEntService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/payment-ents');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(paymentEnt: NewPaymentEnt): Observable<EntityResponseType> {
    return this.http.post<IPaymentEnt>(this.resourceUrl, paymentEnt, { observe: 'response' });
  }

  update(paymentEnt: IPaymentEnt): Observable<EntityResponseType> {
    return this.http.put<IPaymentEnt>(`${this.resourceUrl}/${this.getPaymentEntIdentifier(paymentEnt)}`, paymentEnt, {
      observe: 'response',
    });
  }

  partialUpdate(paymentEnt: PartialUpdatePaymentEnt): Observable<EntityResponseType> {
    return this.http.patch<IPaymentEnt>(`${this.resourceUrl}/${this.getPaymentEntIdentifier(paymentEnt)}`, paymentEnt, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPaymentEnt>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPaymentEnt[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getPaymentEntIdentifier(paymentEnt: Pick<IPaymentEnt, 'id'>): number {
    return paymentEnt.id;
  }

  comparePaymentEnt(o1: Pick<IPaymentEnt, 'id'> | null, o2: Pick<IPaymentEnt, 'id'> | null): boolean {
    return o1 && o2 ? this.getPaymentEntIdentifier(o1) === this.getPaymentEntIdentifier(o2) : o1 === o2;
  }

  addPaymentEntToCollectionIfMissing<Type extends Pick<IPaymentEnt, 'id'>>(
    paymentEntCollection: Type[],
    ...paymentEntsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const paymentEnts: Type[] = paymentEntsToCheck.filter(isPresent);
    if (paymentEnts.length > 0) {
      const paymentEntCollectionIdentifiers = paymentEntCollection.map(paymentEntItem => this.getPaymentEntIdentifier(paymentEntItem)!);
      const paymentEntsToAdd = paymentEnts.filter(paymentEntItem => {
        const paymentEntIdentifier = this.getPaymentEntIdentifier(paymentEntItem);
        if (paymentEntCollectionIdentifiers.includes(paymentEntIdentifier)) {
          return false;
        }
        paymentEntCollectionIdentifiers.push(paymentEntIdentifier);
        return true;
      });
      return [...paymentEntsToAdd, ...paymentEntCollection];
    }
    return paymentEntCollection;
  }
}
