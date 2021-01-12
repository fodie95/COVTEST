import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { TestCovidTestcovidService } from 'app/entities/test-covid-testcovid/test-covid-testcovid.service';
import { ITestCovidTestcovid, TestCovidTestcovid } from 'app/shared/model/test-covid-testcovid.model';

describe('Service Tests', () => {
  describe('TestCovidTestcovid Service', () => {
    let injector: TestBed;
    let service: TestCovidTestcovidService;
    let httpMock: HttpTestingController;
    let elemDefault: ITestCovidTestcovid;
    let expectedResult: ITestCovidTestcovid | ITestCovidTestcovid[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(TestCovidTestcovidService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new TestCovidTestcovid('ID', false, currentDate, 'image/png', 'AAAAAAA', 'AAAAAAA', false);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            dateTest: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find('123').subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a TestCovidTestcovid', () => {
        const returnedFromService = Object.assign(
          {
            id: 'ID',
            dateTest: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateTest: currentDate,
          },
          returnedFromService
        );

        service.create(new TestCovidTestcovid()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a TestCovidTestcovid', () => {
        const returnedFromService = Object.assign(
          {
            positive: true,
            dateTest: currentDate.format(DATE_TIME_FORMAT),
            resulatTest: 'BBBBBB',
            secretKey: 'BBBBBB',
            recupered: true,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateTest: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of TestCovidTestcovid', () => {
        const returnedFromService = Object.assign(
          {
            positive: true,
            dateTest: currentDate.format(DATE_TIME_FORMAT),
            resulatTest: 'BBBBBB',
            secretKey: 'BBBBBB',
            recupered: true,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateTest: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a TestCovidTestcovid', () => {
        service.delete('123').subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
