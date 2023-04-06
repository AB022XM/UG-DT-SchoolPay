import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { AssociatedFeesFormService } from './associated-fees-form.service';
import { AssociatedFeesService } from '../service/associated-fees.service';
import { IAssociatedFees } from '../associated-fees.model';
import { IPaymentEnt } from 'app/entities/payment-ent/payment-ent.model';
import { PaymentEntService } from 'app/entities/payment-ent/service/payment-ent.service';

import { AssociatedFeesUpdateComponent } from './associated-fees-update.component';

describe('AssociatedFees Management Update Component', () => {
  let comp: AssociatedFeesUpdateComponent;
  let fixture: ComponentFixture<AssociatedFeesUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let associatedFeesFormService: AssociatedFeesFormService;
  let associatedFeesService: AssociatedFeesService;
  let paymentEntService: PaymentEntService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [AssociatedFeesUpdateComponent],
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
      .overrideTemplate(AssociatedFeesUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AssociatedFeesUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    associatedFeesFormService = TestBed.inject(AssociatedFeesFormService);
    associatedFeesService = TestBed.inject(AssociatedFeesService);
    paymentEntService = TestBed.inject(PaymentEntService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call PaymentEnt query and add missing value', () => {
      const associatedFees: IAssociatedFees = { id: 456 };
      const paymentEnt: IPaymentEnt = { id: 39583 };
      associatedFees.paymentEnt = paymentEnt;

      const paymentEntCollection: IPaymentEnt[] = [{ id: 68932 }];
      jest.spyOn(paymentEntService, 'query').mockReturnValue(of(new HttpResponse({ body: paymentEntCollection })));
      const additionalPaymentEnts = [paymentEnt];
      const expectedCollection: IPaymentEnt[] = [...additionalPaymentEnts, ...paymentEntCollection];
      jest.spyOn(paymentEntService, 'addPaymentEntToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ associatedFees });
      comp.ngOnInit();

      expect(paymentEntService.query).toHaveBeenCalled();
      expect(paymentEntService.addPaymentEntToCollectionIfMissing).toHaveBeenCalledWith(
        paymentEntCollection,
        ...additionalPaymentEnts.map(expect.objectContaining)
      );
      expect(comp.paymentEntsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const associatedFees: IAssociatedFees = { id: 456 };
      const paymentEnt: IPaymentEnt = { id: 86010 };
      associatedFees.paymentEnt = paymentEnt;

      activatedRoute.data = of({ associatedFees });
      comp.ngOnInit();

      expect(comp.paymentEntsSharedCollection).toContain(paymentEnt);
      expect(comp.associatedFees).toEqual(associatedFees);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAssociatedFees>>();
      const associatedFees = { id: 123 };
      jest.spyOn(associatedFeesFormService, 'getAssociatedFees').mockReturnValue(associatedFees);
      jest.spyOn(associatedFeesService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ associatedFees });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: associatedFees }));
      saveSubject.complete();

      // THEN
      expect(associatedFeesFormService.getAssociatedFees).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(associatedFeesService.update).toHaveBeenCalledWith(expect.objectContaining(associatedFees));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAssociatedFees>>();
      const associatedFees = { id: 123 };
      jest.spyOn(associatedFeesFormService, 'getAssociatedFees').mockReturnValue({ id: null });
      jest.spyOn(associatedFeesService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ associatedFees: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: associatedFees }));
      saveSubject.complete();

      // THEN
      expect(associatedFeesFormService.getAssociatedFees).toHaveBeenCalled();
      expect(associatedFeesService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAssociatedFees>>();
      const associatedFees = { id: 123 };
      jest.spyOn(associatedFeesService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ associatedFees });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(associatedFeesService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('comparePaymentEnt', () => {
      it('Should forward to paymentEntService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(paymentEntService, 'comparePaymentEnt');
        comp.comparePaymentEnt(entity, entity2);
        expect(paymentEntService.comparePaymentEnt).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
