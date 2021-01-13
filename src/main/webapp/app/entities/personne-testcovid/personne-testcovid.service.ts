import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, SearchWithPagination } from 'app/shared/util/request-util';
import { IPersonneTestcovid } from 'app/shared/model/personne-testcovid.model';

type EntityResponseType = HttpResponse<IPersonneTestcovid>;
type EntityArrayResponseType = HttpResponse<IPersonneTestcovid[]>;

@Injectable({ providedIn: 'root' })
export class PersonneTestcovidService {
  public resourceUrl = SERVER_API_URL + 'api/personnes';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/personnes';

  constructor(protected http: HttpClient) {}

  create(personne: IPersonneTestcovid): Observable<EntityResponseType> {
    return this.http.post<IPersonneTestcovid>(this.resourceUrl, personne, { observe: 'response' });
  }

  update(personne: IPersonneTestcovid): Observable<EntityResponseType> {
    return this.http.put<IPersonneTestcovid>(this.resourceUrl, personne, { observe: 'response' });
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<IPersonneTestcovid>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPersonneTestcovid[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPersonneTestcovid[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
