import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IHearing, getHearingIdentifier } from '../hearing.model';

export type EntityResponseType = HttpResponse<IHearing>;
export type EntityArrayResponseType = HttpResponse<IHearing[]>;

@Injectable({ providedIn: 'root' })
export class HearingService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/hearings');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(hearing: IHearing): Observable<EntityResponseType> {
    return this.http.post<IHearing>(this.resourceUrl, hearing, { observe: 'response' });
  }

  update(hearing: IHearing): Observable<EntityResponseType> {
    return this.http.put<IHearing>(`${this.resourceUrl}/${getHearingIdentifier(hearing) as number}`, hearing, { observe: 'response' });
  }

  partialUpdate(hearing: IHearing): Observable<EntityResponseType> {
    return this.http.patch<IHearing>(`${this.resourceUrl}/${getHearingIdentifier(hearing) as number}`, hearing, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IHearing>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IHearing[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addHearingToCollectionIfMissing(hearingCollection: IHearing[], ...hearingsToCheck: (IHearing | null | undefined)[]): IHearing[] {
    const hearings: IHearing[] = hearingsToCheck.filter(isPresent);
    if (hearings.length > 0) {
      const hearingCollectionIdentifiers = hearingCollection.map(hearingItem => getHearingIdentifier(hearingItem)!);
      const hearingsToAdd = hearings.filter(hearingItem => {
        const hearingIdentifier = getHearingIdentifier(hearingItem);
        if (hearingIdentifier == null || hearingCollectionIdentifiers.includes(hearingIdentifier)) {
          return false;
        }
        hearingCollectionIdentifiers.push(hearingIdentifier);
        return true;
      });
      return [...hearingsToAdd, ...hearingCollection];
    }
    return hearingCollection;
  }
}
