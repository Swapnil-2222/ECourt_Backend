import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ILawyerDetails, LawyerDetails } from '../lawyer-details.model';
import { LawyerDetailsService } from '../service/lawyer-details.service';
import { ICourtCase } from 'app/entities/court-case/court-case.model';
import { CourtCaseService } from 'app/entities/court-case/service/court-case.service';
import { UserType } from 'app/entities/enumerations/user-type.model';
import { LawyerType } from 'app/entities/enumerations/lawyer-type.model';

@Component({
  selector: 'jhi-lawyer-details-update',
  templateUrl: './lawyer-details-update.component.html',
})
export class LawyerDetailsUpdateComponent implements OnInit {
  isSaving = false;
  userTypeValues = Object.keys(UserType);
  lawyerTypeValues = Object.keys(LawyerType);

  courtCasesSharedCollection: ICourtCase[] = [];

  editForm = this.fb.group({
    id: [],
    fullName: [],
    contactNo: [],
    emailId: [],
    barRegnNo: [],
    address: [],
    lawyerExperience: [],
    freefield1: [],
    freefield2: [],
    userType: [],
    lawyerType: [],
    isActivated: [],
    lastModifiedBy: [],
    lastModified: [],
    courtCases: [],
  });

  constructor(
    protected lawyerDetailsService: LawyerDetailsService,
    protected courtCaseService: CourtCaseService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ lawyerDetails }) => {
      this.updateForm(lawyerDetails);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const lawyerDetails = this.createFromForm();
    if (lawyerDetails.id !== undefined) {
      this.subscribeToSaveResponse(this.lawyerDetailsService.update(lawyerDetails));
    } else {
      this.subscribeToSaveResponse(this.lawyerDetailsService.create(lawyerDetails));
    }
  }

  trackCourtCaseById(_index: number, item: ICourtCase): number {
    return item.id!;
  }

  getSelectedCourtCase(option: ICourtCase, selectedVals?: ICourtCase[]): ICourtCase {
    if (selectedVals) {
      for (const selectedVal of selectedVals) {
        if (option.id === selectedVal.id) {
          return selectedVal;
        }
      }
    }
    return option;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILawyerDetails>>): void {
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

  protected updateForm(lawyerDetails: ILawyerDetails): void {
    this.editForm.patchValue({
      id: lawyerDetails.id,
      fullName: lawyerDetails.fullName,
      contactNo: lawyerDetails.contactNo,
      emailId: lawyerDetails.emailId,
      barRegnNo: lawyerDetails.barRegnNo,
      address: lawyerDetails.address,
      lawyerExperience: lawyerDetails.lawyerExperience,
      freefield1: lawyerDetails.freefield1,
      freefield2: lawyerDetails.freefield2,
      userType: lawyerDetails.userType,
      lawyerType: lawyerDetails.lawyerType,
      isActivated: lawyerDetails.isActivated,
      lastModifiedBy: lawyerDetails.lastModifiedBy,
      lastModified: lawyerDetails.lastModified,
      courtCases: lawyerDetails.courtCases,
    });

    this.courtCasesSharedCollection = this.courtCaseService.addCourtCaseToCollectionIfMissing(
      this.courtCasesSharedCollection,
      ...(lawyerDetails.courtCases ?? [])
    );
  }

  protected loadRelationshipsOptions(): void {
    this.courtCaseService
      .query()
      .pipe(map((res: HttpResponse<ICourtCase[]>) => res.body ?? []))
      .pipe(
        map((courtCases: ICourtCase[]) =>
          this.courtCaseService.addCourtCaseToCollectionIfMissing(courtCases, ...(this.editForm.get('courtCases')!.value ?? []))
        )
      )
      .subscribe((courtCases: ICourtCase[]) => (this.courtCasesSharedCollection = courtCases));
  }

  protected createFromForm(): ILawyerDetails {
    return {
      ...new LawyerDetails(),
      id: this.editForm.get(['id'])!.value,
      fullName: this.editForm.get(['fullName'])!.value,
      contactNo: this.editForm.get(['contactNo'])!.value,
      emailId: this.editForm.get(['emailId'])!.value,
      barRegnNo: this.editForm.get(['barRegnNo'])!.value,
      address: this.editForm.get(['address'])!.value,
      lawyerExperience: this.editForm.get(['lawyerExperience'])!.value,
      freefield1: this.editForm.get(['freefield1'])!.value,
      freefield2: this.editForm.get(['freefield2'])!.value,
      userType: this.editForm.get(['userType'])!.value,
      lawyerType: this.editForm.get(['lawyerType'])!.value,
      isActivated: this.editForm.get(['isActivated'])!.value,
      lastModifiedBy: this.editForm.get(['lastModifiedBy'])!.value,
      lastModified: this.editForm.get(['lastModified'])!.value,
      courtCases: this.editForm.get(['courtCases'])!.value,
    };
  }
}
