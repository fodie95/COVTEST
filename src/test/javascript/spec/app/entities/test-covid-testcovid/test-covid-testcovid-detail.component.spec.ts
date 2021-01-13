import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { CovtestTestModule } from '../../../test.module';
import { TestCovidTestcovidDetailComponent } from 'app/entities/test-covid-testcovid/test-covid-testcovid-detail.component';
import { TestCovidTestcovid } from 'app/shared/model/test-covid-testcovid.model';

describe('Component Tests', () => {
  describe('TestCovidTestcovid Management Detail Component', () => {
    let comp: TestCovidTestcovidDetailComponent;
    let fixture: ComponentFixture<TestCovidTestcovidDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ testCovid: new TestCovidTestcovid('123') }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CovtestTestModule],
        declarations: [TestCovidTestcovidDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(TestCovidTestcovidDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TestCovidTestcovidDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load testCovid on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.testCovid).toEqual(jasmine.objectContaining({ id: '123' }));
      });
    });

    describe('byteSize', () => {
      it('Should call byteSize from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'byteSize');
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.byteSize(fakeBase64);

        // THEN
        expect(dataUtils.byteSize).toBeCalledWith(fakeBase64);
      });
    });

    describe('openFile', () => {
      it('Should call openFile from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'openFile');
        const fakeContentType = 'fake content type';
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.openFile(fakeContentType, fakeBase64);

        // THEN
        expect(dataUtils.openFile).toBeCalledWith(fakeContentType, fakeBase64);
      });
    });
  });
});
