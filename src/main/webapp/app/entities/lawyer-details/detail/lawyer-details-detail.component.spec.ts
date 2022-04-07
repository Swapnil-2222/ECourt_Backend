import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { LawyerDetailsDetailComponent } from './lawyer-details-detail.component';

describe('LawyerDetails Management Detail Component', () => {
  let comp: LawyerDetailsDetailComponent;
  let fixture: ComponentFixture<LawyerDetailsDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [LawyerDetailsDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ lawyerDetails: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(LawyerDetailsDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(LawyerDetailsDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load lawyerDetails on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.lawyerDetails).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
