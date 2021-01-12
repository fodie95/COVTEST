import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, convertToParamMap } from '@angular/router';

import { CovtestTestModule } from '../../../test.module';
import { PersonneTestcovidComponent } from 'app/entities/personne-testcovid/personne-testcovid.component';
import { PersonneTestcovidService } from 'app/entities/personne-testcovid/personne-testcovid.service';
import { PersonneTestcovid } from 'app/shared/model/personne-testcovid.model';

describe('Component Tests', () => {
  describe('PersonneTestcovid Management Component', () => {
    let comp: PersonneTestcovidComponent;
    let fixture: ComponentFixture<PersonneTestcovidComponent>;
    let service: PersonneTestcovidService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CovtestTestModule],
        declarations: [PersonneTestcovidComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: {
              data: of({
                defaultSort: 'id,asc',
              }),
              queryParamMap: of(
                convertToParamMap({
                  page: '1',
                  size: '1',
                  sort: 'id,desc',
                })
              ),
            },
          },
        ],
      })
        .overrideTemplate(PersonneTestcovidComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PersonneTestcovidComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PersonneTestcovidService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new PersonneTestcovid('123')],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.personnes && comp.personnes[0]).toEqual(jasmine.objectContaining({ id: '123' }));
    });

    it('should load a page', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new PersonneTestcovid('123')],
            headers,
          })
        )
      );

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.personnes && comp.personnes[0]).toEqual(jasmine.objectContaining({ id: '123' }));
    });

    it('should calculate the sort attribute for an id', () => {
      // WHEN
      comp.ngOnInit();
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['id,desc']);
    });

    it('should calculate the sort attribute for a non-id attribute', () => {
      // INIT
      comp.ngOnInit();

      // GIVEN
      comp.predicate = 'name';

      // WHEN
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['name,desc', 'id']);
    });
  });
});
