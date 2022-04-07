import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { CaseStatus } from 'app/entities/enumerations/case-status.model';
import { IHearing, Hearing } from '../hearing.model';

import { HearingService } from './hearing.service';

describe('Hearing Service', () => {
  let service: HearingService;
  let httpMock: HttpTestingController;
  let elemDefault: IHearing;
  let expectedResult: IHearing | IHearing[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(HearingService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      hearingDate: 'AAAAAAA',
      nextHearingDate: 'AAAAAAA',
      description: 'AAAAAAA',
      previousHearingDate: 'AAAAAAA',
      conclusion: 'AAAAAAA',
      comment: 'AAAAAAA',
      caseStatus: CaseStatus.CREATED,
      freefield1: 'AAAAAAA',
      freefield2: 'AAAAAAA',
      freefield3: 'AAAAAAA',
      freefield4: 'AAAAAAA',
      freefield5: 'AAAAAAA',
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

    it('should create a Hearing', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new Hearing()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Hearing', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          hearingDate: 'BBBBBB',
          nextHearingDate: 'BBBBBB',
          description: 'BBBBBB',
          previousHearingDate: 'BBBBBB',
          conclusion: 'BBBBBB',
          comment: 'BBBBBB',
          caseStatus: 'BBBBBB',
          freefield1: 'BBBBBB',
          freefield2: 'BBBBBB',
          freefield3: 'BBBBBB',
          freefield4: 'BBBBBB',
          freefield5: 'BBBBBB',
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

    it('should partial update a Hearing', () => {
      const patchObject = Object.assign(
        {
          nextHearingDate: 'BBBBBB',
          comment: 'BBBBBB',
          caseStatus: 'BBBBBB',
          freefield3: 'BBBBBB',
          freefield4: 'BBBBBB',
          lastModified: 'BBBBBB',
        },
        new Hearing()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Hearing', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          hearingDate: 'BBBBBB',
          nextHearingDate: 'BBBBBB',
          description: 'BBBBBB',
          previousHearingDate: 'BBBBBB',
          conclusion: 'BBBBBB',
          comment: 'BBBBBB',
          caseStatus: 'BBBBBB',
          freefield1: 'BBBBBB',
          freefield2: 'BBBBBB',
          freefield3: 'BBBBBB',
          freefield4: 'BBBBBB',
          freefield5: 'BBBBBB',
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

    it('should delete a Hearing', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addHearingToCollectionIfMissing', () => {
      it('should add a Hearing to an empty array', () => {
        const hearing: IHearing = { id: 123 };
        expectedResult = service.addHearingToCollectionIfMissing([], hearing);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(hearing);
      });

      it('should not add a Hearing to an array that contains it', () => {
        const hearing: IHearing = { id: 123 };
        const hearingCollection: IHearing[] = [
          {
            ...hearing,
          },
          { id: 456 },
        ];
        expectedResult = service.addHearingToCollectionIfMissing(hearingCollection, hearing);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Hearing to an array that doesn't contain it", () => {
        const hearing: IHearing = { id: 123 };
        const hearingCollection: IHearing[] = [{ id: 456 }];
        expectedResult = service.addHearingToCollectionIfMissing(hearingCollection, hearing);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(hearing);
      });

      it('should add only unique Hearing to an array', () => {
        const hearingArray: IHearing[] = [{ id: 123 }, { id: 456 }, { id: 253 }];
        const hearingCollection: IHearing[] = [{ id: 123 }];
        expectedResult = service.addHearingToCollectionIfMissing(hearingCollection, ...hearingArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const hearing: IHearing = { id: 123 };
        const hearing2: IHearing = { id: 456 };
        expectedResult = service.addHearingToCollectionIfMissing([], hearing, hearing2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(hearing);
        expect(expectedResult).toContain(hearing2);
      });

      it('should accept null and undefined values', () => {
        const hearing: IHearing = { id: 123 };
        expectedResult = service.addHearingToCollectionIfMissing([], null, hearing, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(hearing);
      });

      it('should return initial array if no Hearing is added', () => {
        const hearingCollection: IHearing[] = [{ id: 123 }];
        expectedResult = service.addHearingToCollectionIfMissing(hearingCollection, undefined, null);
        expect(expectedResult).toEqual(hearingCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
