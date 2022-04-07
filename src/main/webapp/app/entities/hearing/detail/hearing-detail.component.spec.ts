import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HearingDetailComponent } from './hearing-detail.component';

describe('Hearing Management Detail Component', () => {
  let comp: HearingDetailComponent;
  let fixture: ComponentFixture<HearingDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [HearingDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ hearing: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(HearingDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(HearingDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load hearing on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.hearing).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
