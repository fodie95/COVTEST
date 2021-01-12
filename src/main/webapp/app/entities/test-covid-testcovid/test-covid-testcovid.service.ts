import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, SearchWithPagination } from 'app/shared/util/request-util';
import { ITestCovidTestcovid } from 'app/shared/model/test-covid-testcovid.model';

type EntityResponseType = HttpResponse<ITestCovidTestcovid>;
type EntityArrayResponseType = HttpResponse<ITestCovidTestcovid[]>;

@Injectable({ providedIn: 'root' })
export class TestCovidTestcovidService {
  public resourceUrl = SERVER_API_URL + 'api/test-covids';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/test-covids';

  constructor(protected http: HttpClient) {}

  create(testCovid: ITestCovidTestcovid): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(testCovid);
    return this.http
      .post<ITestCovidTestcovid>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(testCovid: ITestCovidTestcovid): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(testCovid);
    return this.http
      .put<ITestCovidTestcovid>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http
      .get<ITestCovidTestcovid>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITestCovidTestcovid[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITestCovidTestcovid[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(testCovid: ITestCovidTestcovid): ITestCovidTestcovid {
    const copy: ITestCovidTestcovid = Object.assign({}, testCovid, {
      dateTest: testCovid.dateTest && testCovid.dateTest.isValid() ? testCovid.dateTest.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateTest = res.body.dateTest ? moment(res.body.dateTest) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((testCovid: ITestCovidTestcovid) => {
        testCovid.dateTest = testCovid.dateTest ? moment(testCovid.dateTest) : undefined;
      });
    }
    return res;
  }
}
