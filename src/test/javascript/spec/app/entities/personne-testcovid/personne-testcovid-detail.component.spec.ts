import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CovtestTestModule } from '../../../test.module';
import { PersonneTestcovidDetailComponent } from 'app/entities/personne-testcovid/personne-testcovid-detail.component';
import { PersonneTestcovid } from 'app/shared/model/personne-testcovid.model';

describe('Component Tests', () => {
  describe('PersonneTestcovid Management Detail Component', () => {
    let comp: PersonneTestcovidDetailComponent;
    let fixture: ComponentFixture<PersonneTestcovidDetailComponent>;
    const route = ({ data: of({ personne: new PersonneTestcovid('123') }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CovtestTestModule],
        declarations: [PersonneTestcovidDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(PersonneTestcovidDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PersonneTestcovidDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load personne on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.personne).toEqual(jasmine.objectContaining({ id: '123' }));
      });
    });
  });
});
