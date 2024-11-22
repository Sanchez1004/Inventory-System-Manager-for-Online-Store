import axios from '../js/axios-config';
import { Client } from '../types/client';

export const getAllClients = async (): Promise<Client[]> => {
    const response = await axios.get('/api/clients/all');
    return response.data;
};

export const getClientById = async (id: number): Promise<Client> => {
    const response = await axios.get(`/api/clients/${id}`);
    return response.data;
};

export const updateClient = async (id: number, updatedClient: Client): Promise<Client> => {
    const response = await axios.put(`/api/clients/update/${id.toString()}`, updatedClient);
    return response.data;
};

export const deleteClient = async (id: number): Promise<void> => {
    await axios.delete(`/api/clients/delete/${id}`);
};
