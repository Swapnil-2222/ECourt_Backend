import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IHearing, Hearing } from '../hearing.model';
import { HearingService } from '../service/hearing.service';
import { ICourtCase } from 'app/entities/court-case/court-case.model';
import { CourtCaseService } from 'app/entities/court-case/service/court-case.service';
import { CaseStatus } from 'app/entities/enumerations/case-status.model';

@Component({
  selector: 'jhi-hearing-update',
  templateUrl: './hearing-update.component.html',
})
export class HearingUpdateComponent implements OnInit {
  isSaving = false;
  caseStatusValues = Object.keys(CaseStatus);

  courtCasesSharedCollection: ICourtCase[] = [];

  editForm = this.fb.group({
    id: [],
    hearingDate: [],
    nextHearingDate: [],
    description: [],
    previousHearingDate: [],
    conclusion: [],
    comment: [],
    caseStatus: [],
    freefield1: [],
    freefield2: [],
    freefield3: [],
    freefield4: [],
    freefield5: [],
    lastModifiedBy: [],
    lastModified: [],
    courtCase: [],
  });

  constructor(
    protected hearingService: HearingService,
    protected courtCaseService: CourtCaseService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ hearing }) => {
      this.updateForm(hearing);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const hearing = this.createFromForm();
    if (hearing.id !== undefined) {
      this.subscribeToSaveResponse(this.hearingService.update(hearing));
    } else {
      this.subscribeToSaveResponse(this.hearingService.create(hearing));
    }
  }

  trackCourtCaseById(_index: number, item: ICourtCase): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IHearing>>): void {
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

  protected updateForm(hearing: IHearing): void {
    this.editForm.patchValue({
      id: hearing.id,
      hearingDate: hearing.hearingDate,
      nextHearingDate: hearing.nextHearingDate,
      description: hearing.description,
      previousHearingDate: hearing.previousHearingDate,
      conclusion: hearing.conclusion,
      comment: hearing.comment,
      caseStatus: hearing.caseStatus,
      freefield1: hearing.freefield1,
      freefield2: hearing.freefield2,
      freefield3: hearing.freefield3,
      freefield4: hearing.freefield4,
      freefield5: hearing.freefield5,
      lastModifiedBy: hearing.lastModifiedBy,
      lastModified: hearing.lastModified,
      courtCase: hearing.courtCase,
    });

    this.courtCasesSharedCollection = this.courtCaseService.addCourtCaseToCollectionIfMissing(
      this.courtCasesSharedCollection,
      hearing.courtCase
    );
  }

  protected loadRelationshipsOptions(): void {
    this.courtCaseService
      .query()
      .pipe(map((res: HttpResponse<ICourtCase[]>) => res.body ?? []))
      .pipe(
        map((courtCases: ICourtCase[]) =>
          this.courtCaseService.addCourtCaseToCollectionIfMissing(courtCases, this.editForm.get('courtCase')!.value)
        )
      )
      .subscribe((courtCases: ICourtCase[]) => (this.courtCasesSharedCollection = courtCases));
  }

  protected createFromForm(): IHearing {
    return {
      ...new Hearing(),
      id: this.editForm.get(['id'])!.value,
      hearingDate: this.editForm.get(['hearingDate'])!.value,
      nextHearingDate: this.editForm.get(['nextHearingDate'])!.value,
      description: this.editForm.get(['description'])!.value,
      previousHearingDate: this.editForm.get(['previousHearingDate'])!.value,
      conclusion: this.editForm.get(['conclusion'])!.value,
      comment: this.editForm.get(['comment'])!.value,
      caseStatus: this.editForm.get(['caseStatus'])!.value,
      freefield1: this.editForm.get(['freefield1'])!.value,
      freefield2: this.editForm.get(['freefield2'])!.value,
      freefield3: this.editForm.get(['freefield3'])!.value,
      freefield4: this.editForm.get(['freefield4'])!.value,
      freefield5: this.editForm.get(['freefield5'])!.value,
      lastModifiedBy: this.editForm.get(['lastModifiedBy'])!.value,
      lastModified: this.editForm.get(['lastModified'])!.value,
      courtCase: this.editForm.get(['courtCase'])!.value,
    };
  }
}
