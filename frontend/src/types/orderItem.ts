import { CustomerOrder } from './customerOrder.ts';
import { Inventory } from './Inventory.ts';

export type OrderItem = {
  id: number;
  customerOrder: CustomerOrder;
  inventory: Inventory;
  quantity: number;
  unitPrice: number;
  subtotal?: number;
};
