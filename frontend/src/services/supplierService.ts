import axios from '../js/axios-config';
import { Supplier } from '../types/supplier';

/**
 * Fetch all suppliers.
 * @returns A promise resolving to an array of suppliers.
 */
export const getAllSuppliers = async (): Promise<Supplier[]> => {
  const response = await axios.get('/api/suppliers/all');
  return response.data;
};

/**
 * Fetch a supplier by their ID.
 * @param id - The ID of the supplier to fetch.
 * @returns A promise resolving to the supplier data.
 */
export const getSupplierById = async (id: number): Promise<Supplier> => {
  const response = await axios.get(`/api/suppliers/${id}`);
  return response.data;
};

/**
 * Create a new supplier.
 * @param newSupplier - The supplier data to be created.
 * @returns A promise resolving to the newly created supplier.
 */
export const createSupplier = async (newSupplier: Supplier): Promise<Supplier> => {
  const response = await axios.post('/api/suppliers/create', newSupplier);
  return response.data;
};

/**
 * Update an existing supplier by their ID.
 * @param id - The ID of the supplier to update.
 * @param updatedSupplier - The updated supplier data.
 * @returns A promise resolving to the updated supplier.
 */
export const updateSupplier = async (id: number, updatedSupplier: Supplier): Promise<Supplier> => {
  const response = await axios.put(`/api/suppliers/update/${id}`, updatedSupplier);
  return response.data;
};

/**
 * Delete a supplier by their ID.
 * @param id - The ID of the supplier to delete.
 * @returns A promise that resolves when the supplier is deleted.
 */
export const deleteSupplier = async (id: number | undefined): Promise<void> => {
  await axios.delete(`/api/suppliers/delete/${id}`);
};
