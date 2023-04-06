import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { PaymentChannelEntFormService } from './payment-channel-ent-form.service';
import { PaymentChannelEntService } from '../service/payment-channel-ent.service';
import { IPaymentChannelEnt } from '../payment-channel-ent.model';

import { PaymentChannelEntUpdateComponent } from './payment-channel-ent-update.component';

describe('PaymentChannelEnt Management Update Component', () => {
  let comp: PaymentChannelEntUpdateComponent;
  let fixture: ComponentFixture<PaymentChannelEntUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let paymentChannelEntFormService: PaymentChannelEntFormService;
  let paymentChannelEntService: PaymentChannelEntService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [PaymentChannelEntUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(PaymentChannelEntUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PaymentChannelEntUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    paymentChannelEntFormService = TestBed.inject(PaymentChannelEntFormService);
    paymentChannelEntService = TestBed.inject(PaymentChannelEntService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const paymentChannelEnt: IPaymentChannelEnt = { id: 456 };

      activatedRoute.data = of({ paymentChannelEnt });
      comp.ngOnInit();

      expect(comp.paymentChannelEnt).toEqual(paymentChannelEnt);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPaymentChannelEnt>>();
      const paymentChannelEnt = { id: 123 };
      jest.spyOn(paymentChannelEntFormService, 'getPaymentChannelEnt').mockReturnValue(paymentChannelEnt);
      jest.spyOn(paymentChannelEntService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ paymentChannelEnt });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: paymentChannelEnt }));
      saveSubject.complete();

      // THEN
      expect(paymentChannelEntFormService.getPaymentChannelEnt).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(paymentChannelEntService.update).toHaveBeenCalledWith(expect.objectContaining(paymentChannelEnt));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPaymentChannelEnt>>();
      const paymentChannelEnt = { id: 123 };
      jest.spyOn(paymentChannelEntFormService, 'getPaymentChannelEnt').mockReturnValue({ id: null });
      jest.spyOn(paymentChannelEntService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ paymentChannelEnt: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: paymentChannelEnt }));
      saveSubject.complete();

      // THEN
      expect(paymentChannelEntFormService.getPaymentChannelEnt).toHaveBeenCalled();
      expect(paymentChannelEntService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPaymentChannelEnt>>();
      const paymentChannelEnt = { id: 123 };
      jest.spyOn(paymentChannelEntService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ paymentChannelEnt });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(paymentChannelEntService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
