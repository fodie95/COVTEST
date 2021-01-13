import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, convertToParamMap } from '@angular/router';

import { CovtestTestModule } from '../../../test.module';
import { TestCovidTestcovidComponent } from 'app/entities/test-covid-testcovid/test-covid-testcovid.component';
import { TestCovidTestcovidService } from 'app/entities/test-covid-testcovid/test-covid-testcovid.service';
import { TestCovidTestcovid } from 'app/shared/model/test-covid-testcovid.model';

describe('Component Tests', () => {
  describe('TestCovidTestcovid Management Component', () => {
    let comp: TestCovidTestcovidComponent;
    let fixture: ComponentFixture<TestCovidTestcovidComponent>;
    let service: TestCovidTestcovidService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CovtestTestModule],
        declarations: [TestCovidTestcovidComponent],
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
        .overrideTemplate(TestCovidTestcovidComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TestCovidTestcovidComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TestCovidTestcovidService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new TestCovidTestcovid('123')],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.testCovids && comp.testCovids[0]).toEqual(jasmine.objectContaining({ id: '123' }));
    });

    it('should load a page', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new TestCovidTestcovid('123')],
            headers,
          })
        )
      );

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.testCovids && comp.testCovids[0]).toEqual(jasmine.objectContaining({ id: '123' }));
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
