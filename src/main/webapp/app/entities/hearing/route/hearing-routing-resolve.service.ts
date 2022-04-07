import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IHearing, Hearing } from '../hearing.model';
import { HearingService } from '../service/hearing.service';

@Injectable({ providedIn: 'root' })
export class HearingRoutingResolveService implements Resolve<IHearing> {
  constructor(protected service: HearingService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IHearing> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((hearing: HttpResponse<Hearing>) => {
          if (hearing.body) {
            return of(hearing.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Hearing());
  }
}
