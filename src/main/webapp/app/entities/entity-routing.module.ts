import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'court-case',
        data: { pageTitle: 'eCourtApp.courtCase.home.title' },
        loadChildren: () => import('./court-case/court-case.module').then(m => m.CourtCaseModule),
      },
      {
        path: 'hearing',
        data: { pageTitle: 'eCourtApp.hearing.home.title' },
        loadChildren: () => import('./hearing/hearing.module').then(m => m.HearingModule),
      },
      {
        path: 'lawyer-details',
        data: { pageTitle: 'eCourtApp.lawyerDetails.home.title' },
        loadChildren: () => import('./lawyer-details/lawyer-details.module').then(m => m.LawyerDetailsModule),
      },
      {
        path: 'organization',
        data: { pageTitle: 'eCourtApp.organization.home.title' },
        loadChildren: () => import('./organization/organization.module').then(m => m.OrganizationModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
