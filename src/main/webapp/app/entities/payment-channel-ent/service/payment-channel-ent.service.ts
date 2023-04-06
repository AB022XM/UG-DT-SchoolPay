import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPaymentChannelEnt, NewPaymentChannelEnt } from '../payment-channel-ent.model';

export type PartialUpdatePaymentChannelEnt = Partial<IPaymentChannelEnt> & Pick<IPaymentChannelEnt, 'id'>;

export type EntityResponseType = HttpResponse<IPaymentChannelEnt>;
export type EntityArrayResponseType = HttpResponse<IPaymentChannelEnt[]>;

@Injectable({ providedIn: 'root' })
export class PaymentChannelEntService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/payment-channel-ents');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(paymentChannelEnt: NewPaymentChannelEnt): Observable<EntityResponseType> {
    return this.http.post<IPaymentChannelEnt>(this.resourceUrl, paymentChannelEnt, { observe: 'response' });
  }

  update(paymentChannelEnt: IPaymentChannelEnt): Observable<EntityResponseType> {
    return this.http.put<IPaymentChannelEnt>(
      `${this.resourceUrl}/${this.getPaymentChannelEntIdentifier(paymentChannelEnt)}`,
      paymentChannelEnt,
      { observe: 'response' }
    );
  }

  partialUpdate(paymentChannelEnt: PartialUpdatePaymentChannelEnt): Observable<EntityResponseType> {
    return this.http.patch<IPaymentChannelEnt>(
      `${this.resourceUrl}/${this.getPaymentChannelEntIdentifier(paymentChannelEnt)}`,
      paymentChannelEnt,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPaymentChannelEnt>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPaymentChannelEnt[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getPaymentChannelEntIdentifier(paymentChannelEnt: Pick<IPaymentChannelEnt, 'id'>): number {
    return paymentChannelEnt.id;
  }

  comparePaymentChannelEnt(o1: Pick<IPaymentChannelEnt, 'id'> | null, o2: Pick<IPaymentChannelEnt, 'id'> | null): boolean {
    return o1 && o2 ? this.getPaymentChannelEntIdentifier(o1) === this.getPaymentChannelEntIdentifier(o2) : o1 === o2;
  }

  addPaymentChannelEntToCollectionIfMissing<Type extends Pick<IPaymentChannelEnt, 'id'>>(
    paymentChannelEntCollection: Type[],
    ...paymentChannelEntsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const paymentChannelEnts: Type[] = paymentChannelEntsToCheck.filter(isPresent);
    if (paymentChannelEnts.length > 0) {
      const paymentChannelEntCollectionIdentifiers = paymentChannelEntCollection.map(
        paymentChannelEntItem => this.getPaymentChannelEntIdentifier(paymentChannelEntItem)!
      );
      const paymentChannelEntsToAdd = paymentChannelEnts.filter(paymentChannelEntItem => {
        const paymentChannelEntIdentifier = this.getPaymentChannelEntIdentifier(paymentChannelEntItem);
        if (paymentChannelEntCollectionIdentifiers.includes(paymentChannelEntIdentifier)) {
          return false;
        }
        paymentChannelEntCollectionIdentifiers.push(paymentChannelEntIdentifier);
        return true;
      });
      return [...paymentChannelEntsToAdd, ...paymentChannelEntCollection];
    }
    return paymentChannelEntCollection;
  }
}
