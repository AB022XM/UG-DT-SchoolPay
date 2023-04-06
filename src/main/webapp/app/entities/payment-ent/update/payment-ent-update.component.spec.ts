import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { PaymentEntFormService } from './payment-ent-form.service';
import { PaymentEntService } from '../service/payment-ent.service';
import { IPaymentEnt } from '../payment-ent.model';
import { IBiller } from 'app/entities/biller/biller.model';
import { BillerService } from 'app/entities/biller/service/biller.service';

import { PaymentEntUpdateComponent } from './payment-ent-update.component';

describe('PaymentEnt Management Update Component', () => {
  let comp: PaymentEntUpdateComponent;
  let fixture: ComponentFixture<PaymentEntUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let paymentEntFormService: PaymentEntFormService;
  let paymentEntService: PaymentEntService;
  let billerService: BillerService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [PaymentEntUpdateComponent],
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
      .overrideTemplate(PaymentEntUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PaymentEntUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    paymentEntFormService = TestBed.inject(PaymentEntFormService);
    paymentEntService = TestBed.inject(PaymentEntService);
    billerService = TestBed.inject(BillerService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Biller query and add missing value', () => {
      const paymentEnt: IPaymentEnt = { id: 456 };
      const biller: IBiller = { id: 75840 };
      paymentEnt.biller = biller;

      const billerCollection: IBiller[] = [{ id: 73749 }];
      jest.spyOn(billerService, 'query').mockReturnValue(of(new HttpResponse({ body: billerCollection })));
      const additionalBillers = [biller];
      const expectedCollection: IBiller[] = [...additionalBillers, ...billerCollection];
      jest.spyOn(billerService, 'addBillerToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ paymentEnt });
      comp.ngOnInit();

      expect(billerService.query).toHaveBeenCalled();
      expect(billerService.addBillerToCollectionIfMissing).toHaveBeenCalledWith(
        billerCollection,
        ...additionalBillers.map(expect.objectContaining)
      );
      expect(comp.billersSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const paymentEnt: IPaymentEnt = { id: 456 };
      const biller: IBiller = { id: 63789 };
      paymentEnt.biller = biller;

      activatedRoute.data = of({ paymentEnt });
      comp.ngOnInit();

      expect(comp.billersSharedCollection).toContain(biller);
      expect(comp.paymentEnt).toEqual(paymentEnt);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPaymentEnt>>();
      const paymentEnt = { id: 123 };
      jest.spyOn(paymentEntFormService, 'getPaymentEnt').mockReturnValue(paymentEnt);
      jest.spyOn(paymentEntService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ paymentEnt });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: paymentEnt }));
      saveSubject.complete();

      // THEN
      expect(paymentEntFormService.getPaymentEnt).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(paymentEntService.update).toHaveBeenCalledWith(expect.objectContaining(paymentEnt));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPaymentEnt>>();
      const paymentEnt = { id: 123 };
      jest.spyOn(paymentEntFormService, 'getPaymentEnt').mockReturnValue({ id: null });
      jest.spyOn(paymentEntService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ paymentEnt: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: paymentEnt }));
      saveSubject.complete();

      // THEN
      expect(paymentEntFormService.getPaymentEnt).toHaveBeenCalled();
      expect(paymentEntService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPaymentEnt>>();
      const paymentEnt = { id: 123 };
      jest.spyOn(paymentEntService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ paymentEnt });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(paymentEntService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareBiller', () => {
      it('Should forward to billerService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(billerService, 'compareBiller');
        comp.compareBiller(entity, entity2);
        expect(billerService.compareBiller).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
