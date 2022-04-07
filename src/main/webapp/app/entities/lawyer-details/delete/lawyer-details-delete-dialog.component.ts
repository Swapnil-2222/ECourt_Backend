import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ILawyerDetails } from '../lawyer-details.model';
import { LawyerDetailsService } from '../service/lawyer-details.service';

@Component({
  templateUrl: './lawyer-details-delete-dialog.component.html',
})
export class LawyerDetailsDeleteDialogComponent {
  lawyerDetails?: ILawyerDetails;

  constructor(protected lawyerDetailsService: LawyerDetailsService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.lawyerDetailsService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
