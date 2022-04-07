import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ILawyerDetails, LawyerDetails } from '../lawyer-details.model';
import { LawyerDetailsService } from '../service/lawyer-details.service';

import { LawyerDetailsRoutingResolveService } from './lawyer-details-routing-resolve.service';

describe('LawyerDetails routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: LawyerDetailsRoutingResolveService;
  let service: LawyerDetailsService;
  let resultLawyerDetails: ILawyerDetails | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            snapshot: {
              paramMap: convertToParamMap({}),
            },
          },
        },
      ],
    });
    mockRouter = TestBed.inject(Router);
    jest.spyOn(mockRouter, 'navigate').mockImplementation(() => Promise.resolve(true));
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRoute).snapshot;
    routingResolveService = TestBed.inject(LawyerDetailsRoutingResolveService);
    service = TestBed.inject(LawyerDetailsService);
    resultLawyerDetails = undefined;
  });

  describe('resolve', () => {
    it('should return ILawyerDetails returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultLawyerDetails = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultLawyerDetails).toEqual({ id: 123 });
    });

    it('should return new ILawyerDetails if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultLawyerDetails = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultLawyerDetails).toEqual(new LawyerDetails());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as LawyerDetails })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultLawyerDetails = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultLawyerDetails).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
