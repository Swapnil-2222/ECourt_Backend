import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { HearingComponent } from './list/hearing.component';
import { HearingDetailComponent } from './detail/hearing-detail.component';
import { HearingUpdateComponent } from './update/hearing-update.component';
import { HearingDeleteDialogComponent } from './delete/hearing-delete-dialog.component';
import { HearingRoutingModule } from './route/hearing-routing.module';

@NgModule({
  imports: [SharedModule, HearingRoutingModule],
  declarations: [HearingComponent, HearingDetailComponent, HearingUpdateComponent, HearingDeleteDialogComponent],
  entryComponents: [HearingDeleteDialogComponent],
})
export class HearingModule {}
