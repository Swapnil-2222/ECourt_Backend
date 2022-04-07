import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IHearing } from '../hearing.model';

@Component({
  selector: 'jhi-hearing-detail',
  templateUrl: './hearing-detail.component.html',
})
export class HearingDetailComponent implements OnInit {
  hearing: IHearing | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ hearing }) => {
      this.hearing = hearing;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
