import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CovtestSharedModule } from 'app/shared/shared.module';
import { PersonneTestcovidComponent } from './personne-testcovid.component';
import { PersonneTestcovidDetailComponent } from './personne-testcovid-detail.component';
import { PersonneTestcovidUpdateComponent } from './personne-testcovid-update.component';
import { PersonneTestcovidDeleteDialogComponent } from './personne-testcovid-delete-dialog.component';
import { personneRoute } from './personne-testcovid.route';

@NgModule({
  imports: [CovtestSharedModule, RouterModule.forChild(personneRoute)],
  declarations: [
    PersonneTestcovidComponent,
    PersonneTestcovidDetailComponent,
    PersonneTestcovidUpdateComponent,
    PersonneTestcovidDeleteDialogComponent,
  ],
  entryComponents: [PersonneTestcovidDeleteDialogComponent],
})
export class CovtestPersonneTestcovidModule {}
