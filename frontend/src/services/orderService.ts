import axios from '../js/axios-config';
import { CustomerOrder } from '../types/customerOrder';
import { OrderStatus } from '../types/orderStatus';

/**
 * Fetch all orders.
 * @returns A promise resolving to an array of customer orders.
 */
export const getAllOrders = async (): Promise<CustomerOrder[]> => {
  const response = await axios.get('/api/orders/all');
  return response.data;
};

/**
 * Fetch a single order by its ID.
 * @param id - The ID of the order to fetch.
 * @returns A promise resolving to the customer order data.
 */
export const getOrder = async (id: number): Promise<CustomerOrder> => {
  const response = await axios.get(`/api/orders/${id}`);
  return response.data;
};

/**
 * Fetch all orders for a specific user.
 * @param userId - The ID of the user whose orders are to be fetched.
 * @returns A promise resolving to an array of the user's orders.
 */
export const getUserOrders = async (userId: number): Promise<CustomerOrder[]> => {
  const response = await axios.get(`/api/orders/user/${userId}`);
  return response.data;
};

/**
 * Create a new order.
 * @param newOrder - The customer order data to be created.
 * @returns A promise resolving to the newly created order.
 */
export const createOrder = async (newOrder: CustomerOrder): Promise<CustomerOrder> => {
  const response = await axios.post('/api/orders/create', newOrder);
  return response.data;
};

/**
 * Update an existing order by its ID.
 * @param id - The ID of the order to update.
 * @param updatedOrder - The updated order data.
 * @returns A promise resolving to the updated order.
 */
export const updateOrder = async (id: number, updatedOrder: CustomerOrder): Promise<CustomerOrder> => {
  const response = await axios.put(`/api/orders/update/${id}`, updatedOrder);
  return response.data;
};

/**
 * Delete an order by its ID.
 * @param id - The ID of the order to delete.
 * @returns A promise that resolves when the order is deleted.
 */
export const deleteOrder = async (id: number): Promise<void> => {
  await axios.delete(`/api/orders/delete/${id}`);
};

/**
 * Update the status of an order by its ID.
 * @param id - The ID of the order to update.
 * @param status - The new status for the order.
 * @returns A promise that resolves when the status is updated.
 */
export const updateOrderStatus = async (id: number, status: OrderStatus): Promise<void> => {
  await axios.patch(`/api/orders/update-status/${id}?status=${status}`);
};

/**
 * Confirm an order by its ID.
 * @param id - The ID of the order to confirm.
 * @returns A promise that resolves when the order is confirmed.
 */
export const confirmOrder = async (id: number): Promise<void> => {
  await axios.post(`/api/orders/confirm/${id}`);
};
