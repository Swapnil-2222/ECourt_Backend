import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ILawyerDetails, getLawyerDetailsIdentifier } from '../lawyer-details.model';

export type EntityResponseType = HttpResponse<ILawyerDetails>;
export type EntityArrayResponseType = HttpResponse<ILawyerDetails[]>;

@Injectable({ providedIn: 'root' })
export class LawyerDetailsService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/lawyer-details');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(lawyerDetails: ILawyerDetails): Observable<EntityResponseType> {
    return this.http.post<ILawyerDetails>(this.resourceUrl, lawyerDetails, { observe: 'response' });
  }

  update(lawyerDetails: ILawyerDetails): Observable<EntityResponseType> {
    return this.http.put<ILawyerDetails>(`${this.resourceUrl}/${getLawyerDetailsIdentifier(lawyerDetails) as number}`, lawyerDetails, {
      observe: 'response',
    });
  }

  partialUpdate(lawyerDetails: ILawyerDetails): Observable<EntityResponseType> {
    return this.http.patch<ILawyerDetails>(`${this.resourceUrl}/${getLawyerDetailsIdentifier(lawyerDetails) as number}`, lawyerDetails, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ILawyerDetails>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ILawyerDetails[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addLawyerDetailsToCollectionIfMissing(
    lawyerDetailsCollection: ILawyerDetails[],
    ...lawyerDetailsToCheck: (ILawyerDetails | null | undefined)[]
  ): ILawyerDetails[] {
    const lawyerDetails: ILawyerDetails[] = lawyerDetailsToCheck.filter(isPresent);
    if (lawyerDetails.length > 0) {
      const lawyerDetailsCollectionIdentifiers = lawyerDetailsCollection.map(
        lawyerDetailsItem => getLawyerDetailsIdentifier(lawyerDetailsItem)!
      );
      const lawyerDetailsToAdd = lawyerDetails.filter(lawyerDetailsItem => {
        const lawyerDetailsIdentifier = getLawyerDetailsIdentifier(lawyerDetailsItem);
        if (lawyerDetailsIdentifier == null || lawyerDetailsCollectionIdentifiers.includes(lawyerDetailsIdentifier)) {
          return false;
        }
        lawyerDetailsCollectionIdentifiers.push(lawyerDetailsIdentifier);
        return true;
      });
      return [...lawyerDetailsToAdd, ...lawyerDetailsCollection];
    }
    return lawyerDetailsCollection;
  }
}
