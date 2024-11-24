import { OrderItem } from "./orderItem.ts";

export type CustomerOrder = {
  id: number;
  clientId: string;
  clientCountry: string;
  clientCity: string;
  clientStreet: string;
  clientPhone: string;
  clientEmail: string;
  clientFirstName: string;
  clientLastName: string;
  orderDate: string;
  status: string;
  orderItems: OrderItem[];
  totalAmount: number;
};
