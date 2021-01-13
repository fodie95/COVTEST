import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPersonneTestcovid } from 'app/shared/model/personne-testcovid.model';
import { PersonneTestcovidService } from './personne-testcovid.service';

@Component({
  templateUrl: './personne-testcovid-delete-dialog.component.html',
})
export class PersonneTestcovidDeleteDialogComponent {
  personne?: IPersonneTestcovid;

  constructor(
    protected personneService: PersonneTestcovidService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.personneService.delete(id).subscribe(() => {
      this.eventManager.broadcast('personneListModification');
      this.activeModal.close();
    });
  }
}
