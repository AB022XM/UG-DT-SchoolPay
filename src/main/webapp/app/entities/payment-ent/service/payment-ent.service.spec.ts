import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IPaymentEnt } from '../payment-ent.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../payment-ent.test-samples';

import { PaymentEntService } from './payment-ent.service';

const requireRestSample: IPaymentEnt = {
  ...sampleWithRequiredData,
};

describe('PaymentEnt Service', () => {
  let service: PaymentEntService;
  let httpMock: HttpTestingController;
  let expectedResult: IPaymentEnt | IPaymentEnt[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(PaymentEntService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a PaymentEnt', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const paymentEnt = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(paymentEnt).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a PaymentEnt', () => {
      const paymentEnt = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(paymentEnt).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a PaymentEnt', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of PaymentEnt', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a PaymentEnt', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addPaymentEntToCollectionIfMissing', () => {
      it('should add a PaymentEnt to an empty array', () => {
        const paymentEnt: IPaymentEnt = sampleWithRequiredData;
        expectedResult = service.addPaymentEntToCollectionIfMissing([], paymentEnt);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(paymentEnt);
      });

      it('should not add a PaymentEnt to an array that contains it', () => {
        const paymentEnt: IPaymentEnt = sampleWithRequiredData;
        const paymentEntCollection: IPaymentEnt[] = [
          {
            ...paymentEnt,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addPaymentEntToCollectionIfMissing(paymentEntCollection, paymentEnt);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a PaymentEnt to an array that doesn't contain it", () => {
        const paymentEnt: IPaymentEnt = sampleWithRequiredData;
        const paymentEntCollection: IPaymentEnt[] = [sampleWithPartialData];
        expectedResult = service.addPaymentEntToCollectionIfMissing(paymentEntCollection, paymentEnt);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(paymentEnt);
      });

      it('should add only unique PaymentEnt to an array', () => {
        const paymentEntArray: IPaymentEnt[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const paymentEntCollection: IPaymentEnt[] = [sampleWithRequiredData];
        expectedResult = service.addPaymentEntToCollectionIfMissing(paymentEntCollection, ...paymentEntArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const paymentEnt: IPaymentEnt = sampleWithRequiredData;
        const paymentEnt2: IPaymentEnt = sampleWithPartialData;
        expectedResult = service.addPaymentEntToCollectionIfMissing([], paymentEnt, paymentEnt2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(paymentEnt);
        expect(expectedResult).toContain(paymentEnt2);
      });

      it('should accept null and undefined values', () => {
        const paymentEnt: IPaymentEnt = sampleWithRequiredData;
        expectedResult = service.addPaymentEntToCollectionIfMissing([], null, paymentEnt, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(paymentEnt);
      });

      it('should return initial array if no PaymentEnt is added', () => {
        const paymentEntCollection: IPaymentEnt[] = [sampleWithRequiredData];
        expectedResult = service.addPaymentEntToCollectionIfMissing(paymentEntCollection, undefined, null);
        expect(expectedResult).toEqual(paymentEntCollection);
      });
    });

    describe('comparePaymentEnt', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.comparePaymentEnt(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.comparePaymentEnt(entity1, entity2);
        const compareResult2 = service.comparePaymentEnt(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.comparePaymentEnt(entity1, entity2);
        const compareResult2 = service.comparePaymentEnt(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.comparePaymentEnt(entity1, entity2);
        const compareResult2 = service.comparePaymentEnt(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
