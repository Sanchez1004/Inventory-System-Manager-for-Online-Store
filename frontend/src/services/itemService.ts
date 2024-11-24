import axios from '../js/axios-config';
import { Item } from '../types/item';

/**
 * Create a new item.
 * @param item - The item data to be created.
 * @returns A promise resolving to the created item.
 */
export const createItem = async (item: Item): Promise<Item> => {
  const response = await axios.post('/api/items/create', item);
  return response.data;
};

/**
 * Update an existing item by its ID.
 * @param id - The ID of the item to update.
 * @param updatedItem - The updated item data.
 * @returns A promise resolving to the updated item.
 */
export const updateItem = async (id: number, updatedItem: Item): Promise<Item> => {
  const response = await axios.put(`/api/items/update/${id}`, updatedItem);
  return response.data;
};

/**
 * List all items.
 * @returns A promise resolving to a list of all items.
 */
export const listItems = async (): Promise<Item[]> => {
  const response = await axios.get('/api/items/all');
  return response.data;
};

/**
 * Delete an item by its ID.
 * @param id - The ID of the item to delete.
 * @returns A promise resolving when the item is deleted.
 */
export const deleteItem = async (id: number): Promise<void> => {
  await axios.delete(`/api/items/delete/${id}`);
};
