import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { HearingComponent } from '../list/hearing.component';
import { HearingDetailComponent } from '../detail/hearing-detail.component';
import { HearingUpdateComponent } from '../update/hearing-update.component';
import { HearingRoutingResolveService } from './hearing-routing-resolve.service';

const hearingRoute: Routes = [
  {
    path: '',
    component: HearingComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: HearingDetailComponent,
    resolve: {
      hearing: HearingRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: HearingUpdateComponent,
    resolve: {
      hearing: HearingRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: HearingUpdateComponent,
    resolve: {
      hearing: HearingRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(hearingRoute)],
  exports: [RouterModule],
})
export class HearingRoutingModule {}
