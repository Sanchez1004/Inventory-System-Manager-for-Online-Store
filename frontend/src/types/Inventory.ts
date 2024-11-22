import { Item } from "./item";

export type Inventory = {
  id: number;
  item: Item;
  quantity: number;
  location: string;
  isActive: boolean;
};
