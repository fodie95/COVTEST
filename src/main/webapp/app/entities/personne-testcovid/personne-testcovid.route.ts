import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPersonneTestcovid, PersonneTestcovid } from 'app/shared/model/personne-testcovid.model';
import { PersonneTestcovidService } from './personne-testcovid.service';
import { PersonneTestcovidComponent } from './personne-testcovid.component';
import { PersonneTestcovidDetailComponent } from './personne-testcovid-detail.component';
import { PersonneTestcovidUpdateComponent } from './personne-testcovid-update.component';

@Injectable({ providedIn: 'root' })
export class PersonneTestcovidResolve implements Resolve<IPersonneTestcovid> {
  constructor(private service: PersonneTestcovidService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPersonneTestcovid> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((personne: HttpResponse<PersonneTestcovid>) => {
          if (personne.body) {
            return of(personne.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PersonneTestcovid());
  }
}

export const personneRoute: Routes = [
  {
    path: '',
    component: PersonneTestcovidComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'covtestApp.personne.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PersonneTestcovidDetailComponent,
    resolve: {
      personne: PersonneTestcovidResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'covtestApp.personne.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PersonneTestcovidUpdateComponent,
    resolve: {
      personne: PersonneTestcovidResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'covtestApp.personne.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PersonneTestcovidUpdateComponent,
    resolve: {
      personne: PersonneTestcovidResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'covtestApp.personne.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
