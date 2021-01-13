import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { CovtestTestModule } from '../../../test.module';
import { PersonneTestcovidUpdateComponent } from 'app/entities/personne-testcovid/personne-testcovid-update.component';
import { PersonneTestcovidService } from 'app/entities/personne-testcovid/personne-testcovid.service';
import { PersonneTestcovid } from 'app/shared/model/personne-testcovid.model';

describe('Component Tests', () => {
  describe('PersonneTestcovid Management Update Component', () => {
    let comp: PersonneTestcovidUpdateComponent;
    let fixture: ComponentFixture<PersonneTestcovidUpdateComponent>;
    let service: PersonneTestcovidService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CovtestTestModule],
        declarations: [PersonneTestcovidUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(PersonneTestcovidUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PersonneTestcovidUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PersonneTestcovidService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PersonneTestcovid('123');
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
        const entity = new PersonneTestcovid();
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
