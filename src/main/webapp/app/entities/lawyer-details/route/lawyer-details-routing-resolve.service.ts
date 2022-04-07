import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ILawyerDetails, LawyerDetails } from '../lawyer-details.model';
import { LawyerDetailsService } from '../service/lawyer-details.service';

@Injectable({ providedIn: 'root' })
export class LawyerDetailsRoutingResolveService implements Resolve<ILawyerDetails> {
  constructor(protected service: LawyerDetailsService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ILawyerDetails> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((lawyerDetails: HttpResponse<LawyerDetails>) => {
          if (lawyerDetails.body) {
            return of(lawyerDetails.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new LawyerDetails());
  }
}
