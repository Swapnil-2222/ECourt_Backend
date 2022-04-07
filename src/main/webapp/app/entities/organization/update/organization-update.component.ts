import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IOrganization, Organization } from '../organization.model';
import { OrganizationService } from '../service/organization.service';

@Component({
  selector: 'jhi-organization-update',
  templateUrl: './organization-update.component.html',
})
export class OrganizationUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    organizationName: [],
    contactNo: [],
    address: [],
    emailAddress: [],
    pincode: [],
    website: [],
    freefield1: [],
    freefield2: [],
    isActivated: [],
    lastModifiedBy: [],
    lastModified: [],
    createdOn: [],
  });

  constructor(protected organizationService: OrganizationService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ organization }) => {
      this.updateForm(organization);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const organization = this.createFromForm();
    if (organization.id !== undefined) {
      this.subscribeToSaveResponse(this.organizationService.update(organization));
    } else {
      this.subscribeToSaveResponse(this.organizationService.create(organization));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOrganization>>): void {
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

  protected updateForm(organization: IOrganization): void {
    this.editForm.patchValue({
      id: organization.id,
      organizationName: organization.organizationName,
      contactNo: organization.contactNo,
      address: organization.address,
      emailAddress: organization.emailAddress,
      pincode: organization.pincode,
      website: organization.website,
      freefield1: organization.freefield1,
      freefield2: organization.freefield2,
      isActivated: organization.isActivated,
      lastModifiedBy: organization.lastModifiedBy,
      lastModified: organization.lastModified,
      createdOn: organization.createdOn,
    });
  }

  protected createFromForm(): IOrganization {
    return {
      ...new Organization(),
      id: this.editForm.get(['id'])!.value,
      organizationName: this.editForm.get(['organizationName'])!.value,
      contactNo: this.editForm.get(['contactNo'])!.value,
      address: this.editForm.get(['address'])!.value,
      emailAddress: this.editForm.get(['emailAddress'])!.value,
      pincode: this.editForm.get(['pincode'])!.value,
      website: this.editForm.get(['website'])!.value,
      freefield1: this.editForm.get(['freefield1'])!.value,
      freefield2: this.editForm.get(['freefield2'])!.value,
      isActivated: this.editForm.get(['isActivated'])!.value,
      lastModifiedBy: this.editForm.get(['lastModifiedBy'])!.value,
      lastModified: this.editForm.get(['lastModified'])!.value,
      createdOn: this.editForm.get(['createdOn'])!.value,
    };
  }
}
