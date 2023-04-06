import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PaymentChannelEntDetailComponent } from './payment-channel-ent-detail.component';

describe('PaymentChannelEnt Management Detail Component', () => {
  let comp: PaymentChannelEntDetailComponent;
  let fixture: ComponentFixture<PaymentChannelEntDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PaymentChannelEntDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ paymentChannelEnt: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(PaymentChannelEntDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(PaymentChannelEntDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load paymentChannelEnt on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.paymentChannelEnt).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
