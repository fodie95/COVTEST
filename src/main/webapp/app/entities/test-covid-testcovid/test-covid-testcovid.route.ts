import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITestCovidTestcovid, TestCovidTestcovid } from 'app/shared/model/test-covid-testcovid.model';
import { TestCovidTestcovidService } from './test-covid-testcovid.service';
import { TestCovidTestcovidComponent } from './test-covid-testcovid.component';
import { TestCovidTestcovidDetailComponent } from './test-covid-testcovid-detail.component';
import { TestCovidTestcovidUpdateComponent } from './test-covid-testcovid-update.component';

@Injectable({ providedIn: 'root' })
export class TestCovidTestcovidResolve implements Resolve<ITestCovidTestcovid> {
  constructor(private service: TestCovidTestcovidService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITestCovidTestcovid> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((testCovid: HttpResponse<TestCovidTestcovid>) => {
          if (testCovid.body) {
            return of(testCovid.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TestCovidTestcovid());
  }
}

export const testCovidRoute: Routes = [
  {
    path: '',
    component: TestCovidTestcovidComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'covtestApp.testCovid.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TestCovidTestcovidDetailComponent,
    resolve: {
      testCovid: TestCovidTestcovidResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'covtestApp.testCovid.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TestCovidTestcovidUpdateComponent,
    resolve: {
      testCovid: TestCovidTestcovidResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'covtestApp.testCovid.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TestCovidTestcovidUpdateComponent,
    resolve: {
      testCovid: TestCovidTestcovidResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'covtestApp.testCovid.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
