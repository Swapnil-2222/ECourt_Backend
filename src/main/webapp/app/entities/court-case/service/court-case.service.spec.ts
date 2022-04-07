import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { CaseStatus } from 'app/entities/enumerations/case-status.model';
import { NatureResult } from 'app/entities/enumerations/nature-result.model';
import { CourtType } from 'app/entities/enumerations/court-type.model';
import { ICourtCase, CourtCase } from '../court-case.model';

import { CourtCaseService } from './court-case.service';

describe('CourtCase Service', () => {
  let service: CourtCaseService;
  let httpMock: HttpTestingController;
  let elemDefault: ICourtCase;
  let expectedResult: ICourtCase | ICourtCase[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(CourtCaseService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      landReferenceNo: 'AAAAAAA',
      caseNo: 'AAAAAAA',
      villageName: 'AAAAAAA',
      accuserName: 'AAAAAAA',
      defendantName: 'AAAAAAA',
      accuserLawyer: 'AAAAAAA',
      defendantLawyer: 'AAAAAAA',
      caseOfficer: 'AAAAAAA',
      caseOfficerAdd: 'AAAAAAA',
      totalArea: 'AAAAAAA',
      landAcquisitionOfficerNo: 'AAAAAAA',
      section11JudgmentNo: 'AAAAAAA',
      section4NoticeDate: 'AAAAAAA',
      judgementDate: 'AAAAAAA',
      dateOfDecision: 'AAAAAAA',
      caseFilingDate: 'AAAAAAA',
      caseStatus: CaseStatus.CREATED,
      lastHearingDate: 'AAAAAAA',
      nextHearingDate: 'AAAAAAA',
      natureResult: NatureResult.JUDGEMENT,
      amountDepositeInCourt: 'AAAAAAA',
      claimAmount: 'AAAAAAA',
      depositedChequeNo: 'AAAAAAA',
      depositedchequeDate: 'AAAAAAA',
      addInterestAmountDistCourt: 'AAAAAAA',
      addInterestApplicationNo: 'AAAAAAA',
      addIntChequeNo: 'AAAAAAA',
      addIntChequeDate: 'AAAAAAA',
      bankGuaranteeAppNo: 'AAAAAAA',
      courtName: 'AAAAAAA',
      courtType: CourtType.DISTRICTCOURT,
      isActivated: false,
      freefield1: 'AAAAAAA',
      freefield2: 'AAAAAAA',
      freefield3: 'AAAAAAA',
      freefield4: 'AAAAAAA',
      freefield5: 'AAAAAAA',
      freefield6: 'AAAAAAA',
      freefield7: 'AAAAAAA',
      freefield8: 'AAAAAAA',
      freefield9: 'AAAAAAA',
      freefield10: 'AAAAAAA',
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

    it('should create a CourtCase', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new CourtCase()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a CourtCase', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          landReferenceNo: 'BBBBBB',
          caseNo: 'BBBBBB',
          villageName: 'BBBBBB',
          accuserName: 'BBBBBB',
          defendantName: 'BBBBBB',
          accuserLawyer: 'BBBBBB',
          defendantLawyer: 'BBBBBB',
          caseOfficer: 'BBBBBB',
          caseOfficerAdd: 'BBBBBB',
          totalArea: 'BBBBBB',
          landAcquisitionOfficerNo: 'BBBBBB',
          section11JudgmentNo: 'BBBBBB',
          section4NoticeDate: 'BBBBBB',
          judgementDate: 'BBBBBB',
          dateOfDecision: 'BBBBBB',
          caseFilingDate: 'BBBBBB',
          caseStatus: 'BBBBBB',
          lastHearingDate: 'BBBBBB',
          nextHearingDate: 'BBBBBB',
          natureResult: 'BBBBBB',
          amountDepositeInCourt: 'BBBBBB',
          claimAmount: 'BBBBBB',
          depositedChequeNo: 'BBBBBB',
          depositedchequeDate: 'BBBBBB',
          addInterestAmountDistCourt: 'BBBBBB',
          addInterestApplicationNo: 'BBBBBB',
          addIntChequeNo: 'BBBBBB',
          addIntChequeDate: 'BBBBBB',
          bankGuaranteeAppNo: 'BBBBBB',
          courtName: 'BBBBBB',
          courtType: 'BBBBBB',
          isActivated: true,
          freefield1: 'BBBBBB',
          freefield2: 'BBBBBB',
          freefield3: 'BBBBBB',
          freefield4: 'BBBBBB',
          freefield5: 'BBBBBB',
          freefield6: 'BBBBBB',
          freefield7: 'BBBBBB',
          freefield8: 'BBBBBB',
          freefield9: 'BBBBBB',
          freefield10: 'BBBBBB',
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

    it('should partial update a CourtCase', () => {
      const patchObject = Object.assign(
        {
          landReferenceNo: 'BBBBBB',
          accuserName: 'BBBBBB',
          defendantLawyer: 'BBBBBB',
          totalArea: 'BBBBBB',
          landAcquisitionOfficerNo: 'BBBBBB',
          section4NoticeDate: 'BBBBBB',
          dateOfDecision: 'BBBBBB',
          lastHearingDate: 'BBBBBB',
          amountDepositeInCourt: 'BBBBBB',
          depositedChequeNo: 'BBBBBB',
          addInterestAmountDistCourt: 'BBBBBB',
          isActivated: true,
          freefield2: 'BBBBBB',
          freefield3: 'BBBBBB',
          freefield5: 'BBBBBB',
          freefield8: 'BBBBBB',
          freefield9: 'BBBBBB',
          lastModifiedBy: 'BBBBBB',
        },
        new CourtCase()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of CourtCase', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          landReferenceNo: 'BBBBBB',
          caseNo: 'BBBBBB',
          villageName: 'BBBBBB',
          accuserName: 'BBBBBB',
          defendantName: 'BBBBBB',
          accuserLawyer: 'BBBBBB',
          defendantLawyer: 'BBBBBB',
          caseOfficer: 'BBBBBB',
          caseOfficerAdd: 'BBBBBB',
          totalArea: 'BBBBBB',
          landAcquisitionOfficerNo: 'BBBBBB',
          section11JudgmentNo: 'BBBBBB',
          section4NoticeDate: 'BBBBBB',
          judgementDate: 'BBBBBB',
          dateOfDecision: 'BBBBBB',
          caseFilingDate: 'BBBBBB',
          caseStatus: 'BBBBBB',
          lastHearingDate: 'BBBBBB',
          nextHearingDate: 'BBBBBB',
          natureResult: 'BBBBBB',
          amountDepositeInCourt: 'BBBBBB',
          claimAmount: 'BBBBBB',
          depositedChequeNo: 'BBBBBB',
          depositedchequeDate: 'BBBBBB',
          addInterestAmountDistCourt: 'BBBBBB',
          addInterestApplicationNo: 'BBBBBB',
          addIntChequeNo: 'BBBBBB',
          addIntChequeDate: 'BBBBBB',
          bankGuaranteeAppNo: 'BBBBBB',
          courtName: 'BBBBBB',
          courtType: 'BBBBBB',
          isActivated: true,
          freefield1: 'BBBBBB',
          freefield2: 'BBBBBB',
          freefield3: 'BBBBBB',
          freefield4: 'BBBBBB',
          freefield5: 'BBBBBB',
          freefield6: 'BBBBBB',
          freefield7: 'BBBBBB',
          freefield8: 'BBBBBB',
          freefield9: 'BBBBBB',
          freefield10: 'BBBBBB',
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

    it('should delete a CourtCase', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addCourtCaseToCollectionIfMissing', () => {
      it('should add a CourtCase to an empty array', () => {
        const courtCase: ICourtCase = { id: 123 };
        expectedResult = service.addCourtCaseToCollectionIfMissing([], courtCase);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(courtCase);
      });

      it('should not add a CourtCase to an array that contains it', () => {
        const courtCase: ICourtCase = { id: 123 };
        const courtCaseCollection: ICourtCase[] = [
          {
            ...courtCase,
          },
          { id: 456 },
        ];
        expectedResult = service.addCourtCaseToCollectionIfMissing(courtCaseCollection, courtCase);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a CourtCase to an array that doesn't contain it", () => {
        const courtCase: ICourtCase = { id: 123 };
        const courtCaseCollection: ICourtCase[] = [{ id: 456 }];
        expectedResult = service.addCourtCaseToCollectionIfMissing(courtCaseCollection, courtCase);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(courtCase);
      });

      it('should add only unique CourtCase to an array', () => {
        const courtCaseArray: ICourtCase[] = [{ id: 123 }, { id: 456 }, { id: 24224 }];
        const courtCaseCollection: ICourtCase[] = [{ id: 123 }];
        expectedResult = service.addCourtCaseToCollectionIfMissing(courtCaseCollection, ...courtCaseArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const courtCase: ICourtCase = { id: 123 };
        const courtCase2: ICourtCase = { id: 456 };
        expectedResult = service.addCourtCaseToCollectionIfMissing([], courtCase, courtCase2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(courtCase);
        expect(expectedResult).toContain(courtCase2);
      });

      it('should accept null and undefined values', () => {
        const courtCase: ICourtCase = { id: 123 };
        expectedResult = service.addCourtCaseToCollectionIfMissing([], null, courtCase, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(courtCase);
      });

      it('should return initial array if no CourtCase is added', () => {
        const courtCaseCollection: ICourtCase[] = [{ id: 123 }];
        expectedResult = service.addCourtCaseToCollectionIfMissing(courtCaseCollection, undefined, null);
        expect(expectedResult).toEqual(courtCaseCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
