import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PaymentEntDetailComponent } from './payment-ent-detail.component';

describe('PaymentEnt Management Detail Component', () => {
  let comp: PaymentEntDetailComponent;
  let fixture: ComponentFixture<PaymentEntDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PaymentEntDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ paymentEnt: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(PaymentEntDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(PaymentEntDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load paymentEnt on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.paymentEnt).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
