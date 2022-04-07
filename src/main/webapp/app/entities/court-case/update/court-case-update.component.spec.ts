import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { CourtCaseService } from '../service/court-case.service';
import { ICourtCase, CourtCase } from '../court-case.model';
import { IOrganization } from 'app/entities/organization/organization.model';
import { OrganizationService } from 'app/entities/organization/service/organization.service';

import { CourtCaseUpdateComponent } from './court-case-update.component';

describe('CourtCase Management Update Component', () => {
  let comp: CourtCaseUpdateComponent;
  let fixture: ComponentFixture<CourtCaseUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let courtCaseService: CourtCaseService;
  let organizationService: OrganizationService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [CourtCaseUpdateComponent],
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
      .overrideTemplate(CourtCaseUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CourtCaseUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    courtCaseService = TestBed.inject(CourtCaseService);
    organizationService = TestBed.inject(OrganizationService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Organization query and add missing value', () => {
      const courtCase: ICourtCase = { id: 456 };
      const organization: IOrganization = { id: 72427 };
      courtCase.organization = organization;

      const organizationCollection: IOrganization[] = [{ id: 71914 }];
      jest.spyOn(organizationService, 'query').mockReturnValue(of(new HttpResponse({ body: organizationCollection })));
      const additionalOrganizations = [organization];
      const expectedCollection: IOrganization[] = [...additionalOrganizations, ...organizationCollection];
      jest.spyOn(organizationService, 'addOrganizationToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ courtCase });
      comp.ngOnInit();

      expect(organizationService.query).toHaveBeenCalled();
      expect(organizationService.addOrganizationToCollectionIfMissing).toHaveBeenCalledWith(
        organizationCollection,
        ...additionalOrganizations
      );
      expect(comp.organizationsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const courtCase: ICourtCase = { id: 456 };
      const organization: IOrganization = { id: 4448 };
      courtCase.organization = organization;

      activatedRoute.data = of({ courtCase });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(courtCase));
      expect(comp.organizationsSharedCollection).toContain(organization);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<CourtCase>>();
      const courtCase = { id: 123 };
      jest.spyOn(courtCaseService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ courtCase });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: courtCase }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(courtCaseService.update).toHaveBeenCalledWith(courtCase);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<CourtCase>>();
      const courtCase = new CourtCase();
      jest.spyOn(courtCaseService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ courtCase });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: courtCase }));
      saveSubject.complete();

      // THEN
      expect(courtCaseService.create).toHaveBeenCalledWith(courtCase);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<CourtCase>>();
      const courtCase = { id: 123 };
      jest.spyOn(courtCaseService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ courtCase });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(courtCaseService.update).toHaveBeenCalledWith(courtCase);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackOrganizationById', () => {
      it('Should return tracked Organization primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackOrganizationById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
