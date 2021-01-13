import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { ITestCovidTestcovid, TestCovidTestcovid } from 'app/shared/model/test-covid-testcovid.model';
import { TestCovidTestcovidService } from './test-covid-testcovid.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { IPersonneTestcovid } from 'app/shared/model/personne-testcovid.model';
import { PersonneTestcovidService } from 'app/entities/personne-testcovid/personne-testcovid.service';

@Component({
  selector: 'jhi-test-covid-testcovid-update',
  templateUrl: './test-covid-testcovid-update.component.html',
})
export class TestCovidTestcovidUpdateComponent implements OnInit {
  isSaving = false;
  personnes: IPersonneTestcovid[] = [];

  editForm = this.fb.group({
    id: [],
    positive: [],
    dateTest: [],
    resulatTest: [null, [Validators.required]],
    resulatTestContentType: [],
    secretKey: [],
    recupered: [],
    personneId: [],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected testCovidService: TestCovidTestcovidService,
    protected personneService: PersonneTestcovidService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ testCovid }) => {
      if (!testCovid.id) {
        const today = moment().startOf('day');
        testCovid.dateTest = today;
      }

      this.updateForm(testCovid);

      this.personneService.query().subscribe((res: HttpResponse<IPersonneTestcovid[]>) => (this.personnes = res.body || []));
    });
  }

  updateForm(testCovid: ITestCovidTestcovid): void {
    this.editForm.patchValue({
      id: testCovid.id,
      positive: testCovid.positive,
      dateTest: testCovid.dateTest ? testCovid.dateTest.format(DATE_TIME_FORMAT) : null,
      resulatTest: testCovid.resulatTest,
      resulatTestContentType: testCovid.resulatTestContentType,
      secretKey: testCovid.secretKey,
      recupered: testCovid.recupered,
      personneId: testCovid.personneId,
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  setFileData(event: any, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe(null, (err: JhiFileLoadError) => {
      this.eventManager.broadcast(
        new JhiEventWithContent<AlertError>('covtestApp.error', { ...err, key: 'error.file.' + err.key })
      );
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const testCovid = this.createFromForm();
    if (testCovid.id !== undefined) {
      this.subscribeToSaveResponse(this.testCovidService.update(testCovid));
    } else {
      this.subscribeToSaveResponse(this.testCovidService.create(testCovid));
    }
  }

  private createFromForm(): ITestCovidTestcovid {
    return {
      ...new TestCovidTestcovid(),
      id: this.editForm.get(['id'])!.value,
      positive: this.editForm.get(['positive'])!.value,
      dateTest: this.editForm.get(['dateTest'])!.value ? moment(this.editForm.get(['dateTest'])!.value, DATE_TIME_FORMAT) : undefined,
      resulatTestContentType: this.editForm.get(['resulatTestContentType'])!.value,
      resulatTest: this.editForm.get(['resulatTest'])!.value,
      secretKey: this.editForm.get(['secretKey'])!.value,
      recupered: this.editForm.get(['recupered'])!.value,
      personneId: this.editForm.get(['personneId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITestCovidTestcovid>>): void {
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

  trackById(index: number, item: IPersonneTestcovid): any {
    return item.id;
  }
}
