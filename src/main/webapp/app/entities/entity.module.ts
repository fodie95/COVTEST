import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'personne-testcovid',
        loadChildren: () => import('./personne-testcovid/personne-testcovid.module').then(m => m.CovtestPersonneTestcovidModule),
      },
      {
        path: 'test-covid-testcovid',
        loadChildren: () => import('./test-covid-testcovid/test-covid-testcovid.module').then(m => m.CovtestTestCovidTestcovidModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class CovtestEntityModule {}
