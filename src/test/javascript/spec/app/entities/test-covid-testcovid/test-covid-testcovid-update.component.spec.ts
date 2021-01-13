import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { CovtestTestModule } from '../../../test.module';
import { TestCovidTestcovidUpdateComponent } from 'app/entities/test-covid-testcovid/test-covid-testcovid-update.component';
import { TestCovidTestcovidService } from 'app/entities/test-covid-testcovid/test-covid-testcovid.service';
import { TestCovidTestcovid } from 'app/shared/model/test-covid-testcovid.model';

describe('Component Tests', () => {
  describe('TestCovidTestcovid Management Update Component', () => {
    let comp: TestCovidTestcovidUpdateComponent;
    let fixture: ComponentFixture<TestCovidTestcovidUpdateComponent>;
    let service: TestCovidTestcovidService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CovtestTestModule],
        declarations: [TestCovidTestcovidUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(TestCovidTestcovidUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TestCovidTestcovidUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TestCovidTestcovidService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TestCovidTestcovid('123');
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new TestCovidTestcovid();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
