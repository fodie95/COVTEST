import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPersonneTestcovid } from 'app/shared/model/personne-testcovid.model';

@Component({
  selector: 'jhi-personne-testcovid-detail',
  templateUrl: './personne-testcovid-detail.component.html',
})
export class PersonneTestcovidDetailComponent implements OnInit {
  personne: IPersonneTestcovid | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ personne }) => (this.personne = personne));
  }

  previousState(): void {
    window.history.back();
  }
}
