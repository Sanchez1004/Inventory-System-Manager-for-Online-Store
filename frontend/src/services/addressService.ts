import axios from '../js/axios-config';
import { Address } from '../types/address';

/**
 * Create a new address.
 * @param address - The address data to be created.
 * @returns A promise resolving to the created address.
 */
export const createAddress = async (address: Address): Promise<Address> => {
  const response = await axios.post('/api/address/create', address);
  return response.data;
};

/**
 * Update an existing address by its ID.
 * @param id - The ID of the address to update.
 * @param updatedAddress - The updated address data.
 * @returns A promise resolving to the updated address.
 */
export const updateAddress = async (id: number, updatedAddress: Address): Promise<Address> => {
  const response = await axios.put(`/api/address/update/${id}`, updatedAddress);
  return response.data;
};

/**
 * Retrieve an address by its ID.
 * @param id - The ID of the address to retrieve.
 * @returns A promise resolving to the address data.
 */
export const getAddressById = async (id: number): Promise<Address> => {
  const response = await axios.get(`/api/address/${id}`);
  return response.data;
};

/**
 * Retrieve all addresses.
 * @returns A promise resolving to a list of all addresses.
 */
export const getAllAddresses = async (): Promise<Address[]> => {
  const response = await axios.get('/api/address/all');
  return response.data;
};

/**
 * Delete an address by its ID.
 * @param id - The ID of the address to delete.
 * @returns A promise resolving when the address is deleted.
 */
export const deleteAddress = async (id: number): Promise<void> => {
  await axios.delete(`/api/address/delete/${id}`);
};
