import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ILawyerDetails } from '../lawyer-details.model';

@Component({
  selector: 'jhi-lawyer-details-detail',
  templateUrl: './lawyer-details-detail.component.html',
})
export class LawyerDetailsDetailComponent implements OnInit {
  lawyerDetails: ILawyerDetails | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ lawyerDetails }) => {
      this.lawyerDetails = lawyerDetails;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
