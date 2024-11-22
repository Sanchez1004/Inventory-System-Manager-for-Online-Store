import { Address } from "./address";

export type Supplier = {
  id?: number;
  firstName?: string;
  lastName?: string;
  companyName?: string;
  email: string;
  role?: string;
  address?: Address;
};
