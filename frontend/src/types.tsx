export interface Currencies {
  isoCode: string;
  name: string;
}

export interface Currency {
  isoCode: string;
  name: string;
  rate: number;
  updatedAt: string;
}
