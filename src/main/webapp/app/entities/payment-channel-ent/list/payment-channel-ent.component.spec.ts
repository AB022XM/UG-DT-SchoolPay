import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { PaymentChannelEntService } from '../service/payment-channel-ent.service';

import { PaymentChannelEntComponent } from './payment-channel-ent.component';

describe('PaymentChannelEnt Management Component', () => {
  let comp: PaymentChannelEntComponent;
  let fixture: ComponentFixture<PaymentChannelEntComponent>;
  let service: PaymentChannelEntService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        RouterTestingModule.withRoutes([{ path: 'payment-channel-ent', component: PaymentChannelEntComponent }]),
        HttpClientTestingModule,
      ],
      declarations: [PaymentChannelEntComponent],
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
      .overrideTemplate(PaymentChannelEntComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PaymentChannelEntComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(PaymentChannelEntService);

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
    expect(comp.paymentChannelEnts?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to paymentChannelEntService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getPaymentChannelEntIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getPaymentChannelEntIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
