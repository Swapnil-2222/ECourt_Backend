import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { LawyerDetailsComponent } from './list/lawyer-details.component';
import { LawyerDetailsDetailComponent } from './detail/lawyer-details-detail.component';
import { LawyerDetailsUpdateComponent } from './update/lawyer-details-update.component';
import { LawyerDetailsDeleteDialogComponent } from './delete/lawyer-details-delete-dialog.component';
import { LawyerDetailsRoutingModule } from './route/lawyer-details-routing.module';

@NgModule({
  imports: [SharedModule, LawyerDetailsRoutingModule],
  declarations: [LawyerDetailsComponent, LawyerDetailsDetailComponent, LawyerDetailsUpdateComponent, LawyerDetailsDeleteDialogComponent],
  entryComponents: [LawyerDetailsDeleteDialogComponent],
})
export class LawyerDetailsModule {}
