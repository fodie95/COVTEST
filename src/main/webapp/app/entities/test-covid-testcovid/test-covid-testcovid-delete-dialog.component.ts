import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITestCovidTestcovid } from 'app/shared/model/test-covid-testcovid.model';
import { TestCovidTestcovidService } from './test-covid-testcovid.service';

@Component({
  templateUrl: './test-covid-testcovid-delete-dialog.component.html',
})
export class TestCovidTestcovidDeleteDialogComponent {
  testCovid?: ITestCovidTestcovid;

  constructor(
    protected testCovidService: TestCovidTestcovidService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.testCovidService.delete(id).subscribe(() => {
      this.eventManager.broadcast('testCovidListModification');
      this.activeModal.close();
    });
  }
}
