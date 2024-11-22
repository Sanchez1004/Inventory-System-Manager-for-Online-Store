import axios from '../js/axios-config';
import { Admin } from '../types/admin';

/**
 * Login function to authenticate an admin.
 * @returns A promise resolving to the authentication response containing the token and admin details.
 * @param email
 * @param password
 */
export const login = async (email: string, password: string): Promise<Admin> => {
  const response = await axios.post<Admin>('/api/admins/login', {email, password});
  return response.data;
};

/**
 * Register function to create a new admin.
 * @param admin - The admin data to be registered.
 * @returns A promise resolving to the authentication response containing the token and admin details.
 */
export const register = async (admin: Admin): Promise<Admin> => {
  const response = await axios.post<Admin>('/api/admins/register', admin);
  return response.data;
};
