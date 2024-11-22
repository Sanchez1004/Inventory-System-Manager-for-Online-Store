import { Client } from "./client";
import { OrderItem } from "./orderItem.ts";
import { OrderStatus } from "./orderStatus";

export type CustomerOrder = {
  id: number;
  client: Client;
  orderDate: string;
  status: OrderStatus;
  orderItems: OrderItem[];
  totalAmount: number;
};
