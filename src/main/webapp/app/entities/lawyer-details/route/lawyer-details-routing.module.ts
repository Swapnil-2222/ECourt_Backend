import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { LawyerDetailsComponent } from '../list/lawyer-details.component';
import { LawyerDetailsDetailComponent } from '../detail/lawyer-details-detail.component';
import { LawyerDetailsUpdateComponent } from '../update/lawyer-details-update.component';
import { LawyerDetailsRoutingResolveService } from './lawyer-details-routing-resolve.service';

const lawyerDetailsRoute: Routes = [
  {
    path: '',
    component: LawyerDetailsComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: LawyerDetailsDetailComponent,
    resolve: {
      lawyerDetails: LawyerDetailsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: LawyerDetailsUpdateComponent,
    resolve: {
      lawyerDetails: LawyerDetailsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: LawyerDetailsUpdateComponent,
    resolve: {
      lawyerDetails: LawyerDetailsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(lawyerDetailsRoute)],
  exports: [RouterModule],
})
export class LawyerDetailsRoutingModule {}
