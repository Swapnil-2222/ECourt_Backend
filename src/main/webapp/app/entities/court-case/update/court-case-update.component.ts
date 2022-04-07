import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ICourtCase, CourtCase } from '../court-case.model';
import { CourtCaseService } from '../service/court-case.service';
import { IOrganization } from 'app/entities/organization/organization.model';
import { OrganizationService } from 'app/entities/organization/service/organization.service';
import { CaseStatus } from 'app/entities/enumerations/case-status.model';
import { NatureResult } from 'app/entities/enumerations/nature-result.model';
import { CourtType } from 'app/entities/enumerations/court-type.model';

@Component({
  selector: 'jhi-court-case-update',
  templateUrl: './court-case-update.component.html',
})
export class CourtCaseUpdateComponent implements OnInit {
  isSaving = false;
  caseStatusValues = Object.keys(CaseStatus);
  natureResultValues = Object.keys(NatureResult);
  courtTypeValues = Object.keys(CourtType);

  organizationsSharedCollection: IOrganization[] = [];

  editForm = this.fb.group({
    id: [],
    landReferenceNo: [],
    caseNo: [],
    villageName: [],
    accuserName: [],
    defendantName: [],
    accuserLawyer: [],
    defendantLawyer: [],
    caseOfficer: [],
    caseOfficerAdd: [],
    totalArea: [],
    landAcquisitionOfficerNo: [],
    section11JudgmentNo: [],
    section4NoticeDate: [],
    judgementDate: [],
    dateOfDecision: [],
    caseFilingDate: [],
    caseStatus: [],
    lastHearingDate: [],
    nextHearingDate: [],
    natureResult: [],
    amountDepositeInCourt: [],
    claimAmount: [],
    depositedChequeNo: [],
    depositedchequeDate: [],
    addInterestAmountDistCourt: [],
    addInterestApplicationNo: [],
    addIntChequeNo: [],
    addIntChequeDate: [],
    bankGuaranteeAppNo: [],
    courtName: [],
    courtType: [],
    isActivated: [],
    freefield1: [],
    freefield2: [],
    freefield3: [],
    freefield4: [],
    freefield5: [],
    freefield6: [],
    freefield7: [],
    freefield8: [],
    freefield9: [],
    freefield10: [],
    lastModifiedBy: [],
    lastModified: [],
    organization: [],
  });

  constructor(
    protected courtCaseService: CourtCaseService,
    protected organizationService: OrganizationService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ courtCase }) => {
      this.updateForm(courtCase);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const courtCase = this.createFromForm();
    if (courtCase.id !== undefined) {
      this.subscribeToSaveResponse(this.courtCaseService.update(courtCase));
    } else {
      this.subscribeToSaveResponse(this.courtCaseService.create(courtCase));
    }
  }

  trackOrganizationById(_index: number, item: IOrganization): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICourtCase>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(courtCase: ICourtCase): void {
    this.editForm.patchValue({
      id: courtCase.id,
      landReferenceNo: courtCase.landReferenceNo,
      caseNo: courtCase.caseNo,
      villageName: courtCase.villageName,
      accuserName: courtCase.accuserName,
      defendantName: courtCase.defendantName,
      accuserLawyer: courtCase.accuserLawyer,
      defendantLawyer: courtCase.defendantLawyer,
      caseOfficer: courtCase.caseOfficer,
      caseOfficerAdd: courtCase.caseOfficerAdd,
      totalArea: courtCase.totalArea,
      landAcquisitionOfficerNo: courtCase.landAcquisitionOfficerNo,
      section11JudgmentNo: courtCase.section11JudgmentNo,
      section4NoticeDate: courtCase.section4NoticeDate,
      judgementDate: courtCase.judgementDate,
      dateOfDecision: courtCase.dateOfDecision,
      caseFilingDate: courtCase.caseFilingDate,
      caseStatus: courtCase.caseStatus,
      lastHearingDate: courtCase.lastHearingDate,
      nextHearingDate: courtCase.nextHearingDate,
      natureResult: courtCase.natureResult,
      amountDepositeInCourt: courtCase.amountDepositeInCourt,
      claimAmount: courtCase.claimAmount,
      depositedChequeNo: courtCase.depositedChequeNo,
      depositedchequeDate: courtCase.depositedchequeDate,
      addInterestAmountDistCourt: courtCase.addInterestAmountDistCourt,
      addInterestApplicationNo: courtCase.addInterestApplicationNo,
      addIntChequeNo: courtCase.addIntChequeNo,
      addIntChequeDate: courtCase.addIntChequeDate,
      bankGuaranteeAppNo: courtCase.bankGuaranteeAppNo,
      courtName: courtCase.courtName,
      courtType: courtCase.courtType,
      isActivated: courtCase.isActivated,
      freefield1: courtCase.freefield1,
      freefield2: courtCase.freefield2,
      freefield3: courtCase.freefield3,
      freefield4: courtCase.freefield4,
      freefield5: courtCase.freefield5,
      freefield6: courtCase.freefield6,
      freefield7: courtCase.freefield7,
      freefield8: courtCase.freefield8,
      freefield9: courtCase.freefield9,
      freefield10: courtCase.freefield10,
      lastModifiedBy: courtCase.lastModifiedBy,
      lastModified: courtCase.lastModified,
      organization: courtCase.organization,
    });

    this.organizationsSharedCollection = this.organizationService.addOrganizationToCollectionIfMissing(
      this.organizationsSharedCollection,
      courtCase.organization
    );
  }

  protected loadRelationshipsOptions(): void {
    this.organizationService
      .query()
      .pipe(map((res: HttpResponse<IOrganization[]>) => res.body ?? []))
      .pipe(
        map((organizations: IOrganization[]) =>
          this.organizationService.addOrganizationToCollectionIfMissing(organizations, this.editForm.get('organization')!.value)
        )
      )
      .subscribe((organizations: IOrganization[]) => (this.organizationsSharedCollection = organizations));
  }

  protected createFromForm(): ICourtCase {
    return {
      ...new CourtCase(),
      id: this.editForm.get(['id'])!.value,
      landReferenceNo: this.editForm.get(['landReferenceNo'])!.value,
      caseNo: this.editForm.get(['caseNo'])!.value,
      villageName: this.editForm.get(['villageName'])!.value,
      accuserName: this.editForm.get(['accuserName'])!.value,
      defendantName: this.editForm.get(['defendantName'])!.value,
      accuserLawyer: this.editForm.get(['accuserLawyer'])!.value,
      defendantLawyer: this.editForm.get(['defendantLawyer'])!.value,
      caseOfficer: this.editForm.get(['caseOfficer'])!.value,
      caseOfficerAdd: this.editForm.get(['caseOfficerAdd'])!.value,
      totalArea: this.editForm.get(['totalArea'])!.value,
      landAcquisitionOfficerNo: this.editForm.get(['landAcquisitionOfficerNo'])!.value,
      section11JudgmentNo: this.editForm.get(['section11JudgmentNo'])!.value,
      section4NoticeDate: this.editForm.get(['section4NoticeDate'])!.value,
      judgementDate: this.editForm.get(['judgementDate'])!.value,
      dateOfDecision: this.editForm.get(['dateOfDecision'])!.value,
      caseFilingDate: this.editForm.get(['caseFilingDate'])!.value,
      caseStatus: this.editForm.get(['caseStatus'])!.value,
      lastHearingDate: this.editForm.get(['lastHearingDate'])!.value,
      nextHearingDate: this.editForm.get(['nextHearingDate'])!.value,
      natureResult: this.editForm.get(['natureResult'])!.value,
      amountDepositeInCourt: this.editForm.get(['amountDepositeInCourt'])!.value,
      claimAmount: this.editForm.get(['claimAmount'])!.value,
      depositedChequeNo: this.editForm.get(['depositedChequeNo'])!.value,
      depositedchequeDate: this.editForm.get(['depositedchequeDate'])!.value,
      addInterestAmountDistCourt: this.editForm.get(['addInterestAmountDistCourt'])!.value,
      addInterestApplicationNo: this.editForm.get(['addInterestApplicationNo'])!.value,
      addIntChequeNo: this.editForm.get(['addIntChequeNo'])!.value,
      addIntChequeDate: this.editForm.get(['addIntChequeDate'])!.value,
      bankGuaranteeAppNo: this.editForm.get(['bankGuaranteeAppNo'])!.value,
      courtName: this.editForm.get(['courtName'])!.value,
      courtType: this.editForm.get(['courtType'])!.value,
      isActivated: this.editForm.get(['isActivated'])!.value,
      freefield1: this.editForm.get(['freefield1'])!.value,
      freefield2: this.editForm.get(['freefield2'])!.value,
      freefield3: this.editForm.get(['freefield3'])!.value,
      freefield4: this.editForm.get(['freefield4'])!.value,
      freefield5: this.editForm.get(['freefield5'])!.value,
      freefield6: this.editForm.get(['freefield6'])!.value,
      freefield7: this.editForm.get(['freefield7'])!.value,
      freefield8: this.editForm.get(['freefield8'])!.value,
      freefield9: this.editForm.get(['freefield9'])!.value,
      freefield10: this.editForm.get(['freefield10'])!.value,
      lastModifiedBy: this.editForm.get(['lastModifiedBy'])!.value,
      lastModified: this.editForm.get(['lastModified'])!.value,
      organization: this.editForm.get(['organization'])!.value,
    };
  }
}
