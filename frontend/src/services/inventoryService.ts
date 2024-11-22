import axios from '../js/axios-config';
import { Inventory } from '../types/Inventory.ts';

/**
 * Add a new inventory item.
 * @param inventoryItem - The inventory item to be added.
 * @returns A promise resolving to the added inventory item.
 */
export const addInventory = async (inventoryItem: Inventory): Promise<Inventory> => {
  const response = await axios.post('/api/inventory/add', inventoryItem);
  return response.data;
};

/**
 * Create a new inventory item.
 * @param inventoryItem - The inventory item to be created.
 * @returns A promise resolving to the created inventory item.
 */
export const createInventory = async (inventoryItem: Inventory): Promise<Inventory> => {
  const response = await axios.post('/api/inventory/create', inventoryItem);
  return response.data;
};

/**
 * Update an existing inventory item by its ID.
 * @param id - The ID of the inventory item to update.
 * @param updatedInventory - The updated inventory data.
 * @returns A promise resolving to the updated inventory item.
 */
export const updateInventory = async (id: number, updatedInventory: Inventory): Promise<Inventory> => {
  const response = await axios.put(`/api/inventory/update/${id}`, updatedInventory);
  return response.data;
};

/**
 * Reduce the inventory quantity for an item.
 * @param inventoryItem - The inventory data with reduced quantity.
 * @returns A promise that resolves when the operation is successful.
 */
export const reduceInventory = async (inventoryItem: Inventory): Promise<void> => {
  await axios.put('/api/inventory/reduce', inventoryItem);
};

/**
 * Delete an inventory item by its ID (mark as inactive).
 * @param id - The ID of the inventory item to delete.
 * @returns A promise that resolves when the inventory item is deleted.
 */
export const deleteInventory = async (id: number): Promise<void> => {
  await axios.delete(`/api/inventory/delete/${id}`);
};

/**
 * Retrieve an inventory item by its ID.
 * @param id - The ID of the inventory item to retrieve.
 * @returns A promise resolving to the inventory item data.
 */
export const getInventoryById = async (id: number): Promise<Inventory> => {
  const response = await axios.get(`/api/inventory/${id}`);
  return response.data;
};

/**
 * Retrieve all inventory items.
 * @returns A promise resolving to an array of all inventory items.
 */
export const getAllInventory = async (): Promise<Inventory[]> => {
  const response = await axios.get('/api/inventory/all');
  return response.data;
};
