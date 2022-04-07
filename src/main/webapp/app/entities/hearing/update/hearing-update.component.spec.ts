import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { HearingService } from '../service/hearing.service';
import { IHearing, Hearing } from '../hearing.model';
import { ICourtCase } from 'app/entities/court-case/court-case.model';
import { CourtCaseService } from 'app/entities/court-case/service/court-case.service';

import { HearingUpdateComponent } from './hearing-update.component';

describe('Hearing Management Update Component', () => {
  let comp: HearingUpdateComponent;
  let fixture: ComponentFixture<HearingUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let hearingService: HearingService;
  let courtCaseService: CourtCaseService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [HearingUpdateComponent],
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
      .overrideTemplate(HearingUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(HearingUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    hearingService = TestBed.inject(HearingService);
    courtCaseService = TestBed.inject(CourtCaseService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call CourtCase query and add missing value', () => {
      const hearing: IHearing = { id: 456 };
      const courtCase: ICourtCase = { id: 77892 };
      hearing.courtCase = courtCase;

      const courtCaseCollection: ICourtCase[] = [{ id: 87759 }];
      jest.spyOn(courtCaseService, 'query').mockReturnValue(of(new HttpResponse({ body: courtCaseCollection })));
      const additionalCourtCases = [courtCase];
      const expectedCollection: ICourtCase[] = [...additionalCourtCases, ...courtCaseCollection];
      jest.spyOn(courtCaseService, 'addCourtCaseToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ hearing });
      comp.ngOnInit();

      expect(courtCaseService.query).toHaveBeenCalled();
      expect(courtCaseService.addCourtCaseToCollectionIfMissing).toHaveBeenCalledWith(courtCaseCollection, ...additionalCourtCases);
      expect(comp.courtCasesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const hearing: IHearing = { id: 456 };
      const courtCase: ICourtCase = { id: 58703 };
      hearing.courtCase = courtCase;

      activatedRoute.data = of({ hearing });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(hearing));
      expect(comp.courtCasesSharedCollection).toContain(courtCase);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Hearing>>();
      const hearing = { id: 123 };
      jest.spyOn(hearingService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ hearing });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: hearing }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(hearingService.update).toHaveBeenCalledWith(hearing);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Hearing>>();
      const hearing = new Hearing();
      jest.spyOn(hearingService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ hearing });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: hearing }));
      saveSubject.complete();

      // THEN
      expect(hearingService.create).toHaveBeenCalledWith(hearing);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Hearing>>();
      const hearing = { id: 123 };
      jest.spyOn(hearingService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ hearing });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(hearingService.update).toHaveBeenCalledWith(hearing);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackCourtCaseById', () => {
      it('Should return tracked CourtCase primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackCourtCaseById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
