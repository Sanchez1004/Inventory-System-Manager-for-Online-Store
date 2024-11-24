export type OrderItem = {
  id: number;
  inventoryId: number;
  inventoryName: string;
  quantity: number;
  unitPrice: number;
  subtotal?: number;
};
