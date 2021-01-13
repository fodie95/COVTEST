export interface IPersonneTestcovid {
  id?: string;
  nom?: string;
  prenom?: string;
  nni?: string;
  tel?: string;
  whatsapp?: string;
  email?: string;
  adresse?: string;
}

export class PersonneTestcovid implements IPersonneTestcovid {
  constructor(
    public id?: string,
    public nom?: string,
    public prenom?: string,
    public nni?: string,
    public tel?: string,
    public whatsapp?: string,
    public email?: string,
    public adresse?: string
  ) {}
}
