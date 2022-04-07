import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { UserType } from 'app/entities/enumerations/user-type.model';
import { LawyerType } from 'app/entities/enumerations/lawyer-type.model';
import { ILawyerDetails, LawyerDetails } from '../lawyer-details.model';

import { LawyerDetailsService } from './lawyer-details.service';

describe('LawyerDetails Service', () => {
  let service: LawyerDetailsService;
  let httpMock: HttpTestingController;
  let elemDefault: ILawyerDetails;
  let expectedResult: ILawyerDetails | ILawyerDetails[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(LawyerDetailsService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      fullName: 'AAAAAAA',
      contactNo: 'AAAAAAA',
      emailId: 'AAAAAAA',
      barRegnNo: 'AAAAAAA',
      address: 'AAAAAAA',
      lawyerExperience: 'AAAAAAA',
      freefield1: 'AAAAAAA',
      freefield2: 'AAAAAAA',
      userType: UserType.USERTYPE,
      lawyerType: LawyerType.PRIVATELAWYER,
      isActivated: false,
      lastModifiedBy: 'AAAAAAA',
      lastModified: 'AAAAAAA',
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign({}, elemDefault);

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a LawyerDetails', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new LawyerDetails()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a LawyerDetails', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          fullName: 'BBBBBB',
          contactNo: 'BBBBBB',
          emailId: 'BBBBBB',
          barRegnNo: 'BBBBBB',
          address: 'BBBBBB',
          lawyerExperience: 'BBBBBB',
          freefield1: 'BBBBBB',
          freefield2: 'BBBBBB',
          userType: 'BBBBBB',
          lawyerType: 'BBBBBB',
          isActivated: true,
          lastModifiedBy: 'BBBBBB',
          lastModified: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a LawyerDetails', () => {
      const patchObject = Object.assign(
        {
          fullName: 'BBBBBB',
          contactNo: 'BBBBBB',
          emailId: 'BBBBBB',
          barRegnNo: 'BBBBBB',
          address: 'BBBBBB',
          lawyerExperience: 'BBBBBB',
          freefield1: 'BBBBBB',
          userType: 'BBBBBB',
          lastModifiedBy: 'BBBBBB',
          lastModified: 'BBBBBB',
        },
        new LawyerDetails()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of LawyerDetails', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          fullName: 'BBBBBB',
          contactNo: 'BBBBBB',
          emailId: 'BBBBBB',
          barRegnNo: 'BBBBBB',
          address: 'BBBBBB',
          lawyerExperience: 'BBBBBB',
          freefield1: 'BBBBBB',
          freefield2: 'BBBBBB',
          userType: 'BBBBBB',
          lawyerType: 'BBBBBB',
          isActivated: true,
          lastModifiedBy: 'BBBBBB',
          lastModified: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a LawyerDetails', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addLawyerDetailsToCollectionIfMissing', () => {
      it('should add a LawyerDetails to an empty array', () => {
        const lawyerDetails: ILawyerDetails = { id: 123 };
        expectedResult = service.addLawyerDetailsToCollectionIfMissing([], lawyerDetails);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(lawyerDetails);
      });

      it('should not add a LawyerDetails to an array that contains it', () => {
        const lawyerDetails: ILawyerDetails = { id: 123 };
        const lawyerDetailsCollection: ILawyerDetails[] = [
          {
            ...lawyerDetails,
          },
          { id: 456 },
        ];
        expectedResult = service.addLawyerDetailsToCollectionIfMissing(lawyerDetailsCollection, lawyerDetails);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a LawyerDetails to an array that doesn't contain it", () => {
        const lawyerDetails: ILawyerDetails = { id: 123 };
        const lawyerDetailsCollection: ILawyerDetails[] = [{ id: 456 }];
        expectedResult = service.addLawyerDetailsToCollectionIfMissing(lawyerDetailsCollection, lawyerDetails);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(lawyerDetails);
      });

      it('should add only unique LawyerDetails to an array', () => {
        const lawyerDetailsArray: ILawyerDetails[] = [{ id: 123 }, { id: 456 }, { id: 4558 }];
        const lawyerDetailsCollection: ILawyerDetails[] = [{ id: 123 }];
        expectedResult = service.addLawyerDetailsToCollectionIfMissing(lawyerDetailsCollection, ...lawyerDetailsArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const lawyerDetails: ILawyerDetails = { id: 123 };
        const lawyerDetails2: ILawyerDetails = { id: 456 };
        expectedResult = service.addLawyerDetailsToCollectionIfMissing([], lawyerDetails, lawyerDetails2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(lawyerDetails);
        expect(expectedResult).toContain(lawyerDetails2);
      });

      it('should accept null and undefined values', () => {
        const lawyerDetails: ILawyerDetails = { id: 123 };
        expectedResult = service.addLawyerDetailsToCollectionIfMissing([], null, lawyerDetails, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(lawyerDetails);
      });

      it('should return initial array if no LawyerDetails is added', () => {
        const lawyerDetailsCollection: ILawyerDetails[] = [{ id: 123 }];
        expectedResult = service.addLawyerDetailsToCollectionIfMissing(lawyerDetailsCollection, undefined, null);
        expect(expectedResult).toEqual(lawyerDetailsCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
