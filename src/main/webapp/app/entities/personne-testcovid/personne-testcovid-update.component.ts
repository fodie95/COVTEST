import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IPersonneTestcovid, PersonneTestcovid } from 'app/shared/model/personne-testcovid.model';
import { PersonneTestcovidService } from './personne-testcovid.service';

@Component({
  selector: 'jhi-personne-testcovid-update',
  templateUrl: './personne-testcovid-update.component.html',
})
export class PersonneTestcovidUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    nom: [],
    prenom: [],
    nni: [null, []],
    tel: [null, []],
    whatsapp: [null, []],
    email: [null, []],
    adresse: [],
  });

  constructor(protected personneService: PersonneTestcovidService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ personne }) => {
      this.updateForm(personne);
    });
  }

  updateForm(personne: IPersonneTestcovid): void {
    this.editForm.patchValue({
      id: personne.id,
      nom: personne.nom,
      prenom: personne.prenom,
      nni: personne.nni,
      tel: personne.tel,
      whatsapp: personne.whatsapp,
      email: personne.email,
      adresse: personne.adresse,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const personne = this.createFromForm();
    if (personne.id !== undefined) {
      this.subscribeToSaveResponse(this.personneService.update(personne));
    } else {
      this.subscribeToSaveResponse(this.personneService.create(personne));
    }
  }

  private createFromForm(): IPersonneTestcovid {
    return {
      ...new PersonneTestcovid(),
      id: this.editForm.get(['id'])!.value,
      nom: this.editForm.get(['nom'])!.value,
      prenom: this.editForm.get(['prenom'])!.value,
      nni: this.editForm.get(['nni'])!.value,
      tel: this.editForm.get(['tel'])!.value,
      whatsapp: this.editForm.get(['whatsapp'])!.value,
      email: this.editForm.get(['email'])!.value,
      adresse: this.editForm.get(['adresse'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPersonneTestcovid>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
