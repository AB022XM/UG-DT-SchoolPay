import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IPaymentChannelEnt } from '../payment-channel-ent.model';
import { PaymentChannelEntService } from '../service/payment-channel-ent.service';

import { PaymentChannelEntRoutingResolveService } from './payment-channel-ent-routing-resolve.service';

describe('PaymentChannelEnt routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: PaymentChannelEntRoutingResolveService;
  let service: PaymentChannelEntService;
  let resultPaymentChannelEnt: IPaymentChannelEnt | null | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            snapshot: {
              paramMap: convertToParamMap({}),
            },
          },
        },
      ],
    });
    mockRouter = TestBed.inject(Router);
    jest.spyOn(mockRouter, 'navigate').mockImplementation(() => Promise.resolve(true));
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRoute).snapshot;
    routingResolveService = TestBed.inject(PaymentChannelEntRoutingResolveService);
    service = TestBed.inject(PaymentChannelEntService);
    resultPaymentChannelEnt = undefined;
  });

  describe('resolve', () => {
    it('should return IPaymentChannelEnt returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultPaymentChannelEnt = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultPaymentChannelEnt).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultPaymentChannelEnt = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultPaymentChannelEnt).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IPaymentChannelEnt>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultPaymentChannelEnt = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultPaymentChannelEnt).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
