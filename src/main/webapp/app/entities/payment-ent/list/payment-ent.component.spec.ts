import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { PaymentEntService } from '../service/payment-ent.service';

import { PaymentEntComponent } from './payment-ent.component';

describe('PaymentEnt Management Component', () => {
  let comp: PaymentEntComponent;
  let fixture: ComponentFixture<PaymentEntComponent>;
  let service: PaymentEntService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RouterTestingModule.withRoutes([{ path: 'payment-ent', component: PaymentEntComponent }]), HttpClientTestingModule],
      declarations: [PaymentEntComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            data: of({
              defaultSort: 'id,asc',
            }),
            queryParamMap: of(
              jest.requireActual('@angular/router').convertToParamMap({
                page: '1',
                size: '1',
                sort: 'id,desc',
              })
            ),
            snapshot: { queryParams: {} },
          },
        },
      ],
    })
      .overrideTemplate(PaymentEntComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PaymentEntComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(PaymentEntService);

    const headers = new HttpHeaders();
    jest.spyOn(service, 'query').mockReturnValue(
      of(
        new HttpResponse({
          body: [{ id: 123 }],
          headers,
        })
      )
    );
  });

  it('Should call load all on init', () => {
    // WHEN
    comp.ngOnInit();

    // THEN
    expect(service.query).toHaveBeenCalled();
    expect(comp.paymentEnts?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to paymentEntService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getPaymentEntIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getPaymentEntIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
