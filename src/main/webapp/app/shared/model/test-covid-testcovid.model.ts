import { Moment } from 'moment';

export interface ITestCovidTestcovid {
  id?: string;
  positive?: boolean;
  dateTest?: Moment;
  resulatTestContentType?: string;
  resulatTest?: any;
  secretKey?: string;
  recupered?: boolean;
  personneId?: string;
}

export class TestCovidTestcovid implements ITestCovidTestcovid {
  constructor(
    public id?: string,
    public positive?: boolean,
    public dateTest?: Moment,
    public resulatTestContentType?: string,
    public resulatTest?: any,
    public secretKey?: string,
    public recupered?: boolean,
    public personneId?: string
  ) {
    this.positive = this.positive || false;
    this.recupered = this.recupered || false;
  }
}
