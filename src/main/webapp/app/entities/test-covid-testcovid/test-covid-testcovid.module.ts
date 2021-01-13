import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CovtestSharedModule } from 'app/shared/shared.module';
import { TestCovidTestcovidComponent } from './test-covid-testcovid.component';
import { TestCovidTestcovidDetailComponent } from './test-covid-testcovid-detail.component';
import { TestCovidTestcovidUpdateComponent } from './test-covid-testcovid-update.component';
import { TestCovidTestcovidDeleteDialogComponent } from './test-covid-testcovid-delete-dialog.component';
import { testCovidRoute } from './test-covid-testcovid.route';

@NgModule({
  imports: [CovtestSharedModule, RouterModule.forChild(testCovidRoute)],
  declarations: [
    TestCovidTestcovidComponent,
    TestCovidTestcovidDetailComponent,
    TestCovidTestcovidUpdateComponent,
    TestCovidTestcovidDeleteDialogComponent,
  ],
  entryComponents: [TestCovidTestcovidDeleteDialogComponent],
})
export class CovtestTestCovidTestcovidModule {}
