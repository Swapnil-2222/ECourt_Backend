import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IHearing } from '../hearing.model';
import { HearingService } from '../service/hearing.service';

@Component({
  templateUrl: './hearing-delete-dialog.component.html',
})
export class HearingDeleteDialogComponent {
  hearing?: IHearing;

  constructor(protected hearingService: HearingService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.hearingService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
