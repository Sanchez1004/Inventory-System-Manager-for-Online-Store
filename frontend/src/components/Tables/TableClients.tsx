import React, { useEffect, useState } from 'react';
import { Client } from '../../types/client';
import { deleteClient, getAllClients, updateClient } from '../../services/clientService';
import { handleNestedChange } from '../../functions/handleNestedChange.tsx';

const TableClients: React.FC = () => {
  const [clients, setClients] = useState<Client[]>([]);
  const [isLoading, setIsLoading] = useState<boolean>(true);
  const [editingClient, setEditingClient] = useState<Client | null>(null);
  const [formData, setFormData] = useState<Client | null>(null);
  const [errors, setErrors] = useState<{ [key: string]: string }>({});
  const [isConfirming, setIsConfirming] = useState(false);
  const [searchTerm, setSearchTerm] = useState<string>('');


  useEffect(() => {
    const fetchClients = async () => {
      try {
        const data = await getAllClients();
        setClients(data);
      } catch (error) {
        console.error('Error fetching data:', error);
      } finally {
        setIsLoading(false);
      }
    };
    fetchClients();
  }, []);

  const handleEditClick = (client: Client) => {
    setEditingClient(client);
    setFormData({ ...client });
    setErrors({});
  };

  const handleFormClose = () => {
    setEditingClient(null);
    setFormData(null);
  };

  const nestedKeys = ["street", "city", "country"];
  const handleInputChange = handleNestedChange(setFormData, 'address', nestedKeys);

  const validateForm = (): boolean => {
    let formErrors: { [key: string]: string } = {};

    if (!formData?.firstName) formErrors.firstName = "First name is required.";
    if (!formData?.lastName) formErrors.lastName = "Last name is required.";
    if (!formData?.email) formErrors.email = "Email is required.";
    if (!formData?.phone) formErrors.companyName = "Phone is required.";
    if (!formData?.address?.city) formErrors.city = "City is required.";

    setErrors(formErrors);
    return Object.keys(formErrors).length === 0;
  };

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();

    if (!validateForm()) return;

    setIsConfirming(true);
  };

  const handleConfirmSave = async () => {
    if (formData && editingClient) {
      console.log(formData);
      try {
        const updatedClient = await updateClient(editingClient.id, formData);
        console.log('Client updated:', updatedClient);

        setClients((prevClients) =>
          prevClients.map((client) =>
            client.id === updatedClient.id ? updatedClient : client
          )
        );
      } catch (error) {
        console.error('Error updating client:', error);
      }
    }
    setIsConfirming(false);
    handleFormClose();
  };

  const handleDeleteClick = async (clientId: number) => {
    const confirmDelete = window.confirm("Are you sure you want to delete this client?");
    if (confirmDelete) {
      try {
        await deleteClient(clientId);
        setClients((prevClients) => prevClients.filter((client) => client.id !== clientId));
      } catch (error) {
        console.error('Error deleting client:', error);
      }
    }
  };

  const renderClientData = () => {
    if (isLoading) {
      return <div className="p-4 text-center">Loading clients...</div>;
    }

    const filteredClients = clients.filter((client) => {
      const regex = new RegExp(searchTerm, 'i');
      return (
        regex.test(client.firstName ?? '') ||
        regex.test(client.lastName ?? '') ||
        regex.test(client.email) ||
        regex.test(client.phone ?? '') ||
        regex.test(client.role) ||
        regex.test(client.address?.city ?? '') ||
        regex.test(client.address?.country ?? '') ||
        regex.test(client.address?.street ?? '')
      );
    });

    if (filteredClients.length > 0) {
      return filteredClients.map((client) => (
        <div
          className="grid grid-cols-12 items-center border-t border-stroke py-4.5 px-4 dark:border-strokedark sm:grid-cols-12 md:px-6 2xl:px-7.5"
          key={client.id}
        >
          <div className="col-span-1 flex">
            <p className="text-sm text-black dark:text-white">{client.id}</p>
          </div>
          <div className="col-span-2 flex">
            <p className="text-sm text-black dark:text-white">
              {client.firstName} {client.lastName}
            </p>
          </div>
          <div className="col-span-3 flex">
            <p className="text-sm text-black dark:text-white place-content-center">{client.email}</p>
          </div>
          <div className="col-span-2 flex place-content-center">
            <p className="text-sm text-black dark:text-white">{client.phone}</p>
          </div>
          <div className="col-span-3 flex place-content-center text-wrap">
            <p className="text-sm text-black dark:text-white text-center text-wrap">
              {client.address?.country}, {client.address?.city}, {client.address?.street}
            </p>
          </div>
          <div className="cols-span-1 flex place-content-center space-x-3.5">
            <button
              className="hover:text-primary"
              title="Edit Client"
              onClick={() => handleEditClick(client)}
            >
              <svg
                xmlns="http://www.w3.org/2000/svg"
                width="16"
                height="16"
                viewBox="0 0 24 24"
                fill="none"
                stroke="currentColor"
                strokeWidth="2"
                strokeLinecap="round"
                strokeLinejoin="round"
                className="feather feather-edit"
              >
                <path d="M11 4H4a2 2 0 0 0-2 2v14a2 2 0 0 0 2 2h14a2 2 0 0 0 2-2v-7"></path>
                <path d="M18.5 2.5a2.121 2.121 0 0 1 3 3L12 15l-4 1 1-4 9.5-9.5z"></path>
              </svg>
            </button>
            <button
              className="hover:text-primary"
              title="Delete client"
              onClick={() => handleDeleteClick(client.id)}
            >
              <svg
                className="fill-current"
                width="18"
                height="18"
                viewBox="0 0 18 18"
                fill="none"
                xmlns="http://www.w3.org/2000/svg"
              >
                <path
                  d="M13.7535 2.47502H11.5879V1.9969C11.5879 1.15315 10.9129 0.478149 10.0691 0.478149H7.90352C7.05977 0.478149 6.38477 1.15315 6.38477 1.9969V2.47502H4.21914C3.40352 2.47502 2.72852 3.15002 2.72852 3.96565V4.8094C2.72852 5.42815 3.09414 5.9344 3.62852 6.1594L4.07852 15.4688C4.13477 16.6219 5.09102 17.5219 6.24414 17.5219H11.7004C12.8535 17.5219 13.8098 16.6219 13.866 15.4688L14.3441 6.13127C14.8785 5.90627 15.2441 5.3719 15.2441 4.78127V3.93752C15.2441 3.15002 14.5691 2.47502 13.7535 2.47502ZM7.67852 1.9969C7.67852 1.85627 7.79102 1.74377 7.93164 1.74377H10.0973C10.2379 1.74377 10.3504 1.85627 10.3504 1.9969V2.47502H7.70664V1.9969H7.67852ZM4.02227 3.96565C4.02227 3.85315 4.10664 3.74065 4.24727 3.74065H13.7535C13.866 3.74065 13.9785 3.82502 13.9785 3.96565V4.8094C13.9785 4.9219 13.8941 5.0344 13.7535 5.0344H4.24727C4.13477 5.0344 4.02227 4.95002 4.02227 4.8094V3.96565ZM11.7285 16.2563H6.27227C5.79414 16.2563 5.40039 15.8906 5.37227 15.3844L4.95039 6.2719H13.0785L12.6566 15.3844C12.6004 15.8625 12.2066 16.2563 11.7285 16.2563Z"
                  fill=""
                />
                <path
                  d="M9.00039 9.11255C8.66289 9.11255 8.35352 9.3938 8.35352 9.75942V13.3313C8.35352 13.6688 8.63477 13.9782 9.00039 13.9782C9.33789 13.9782 9.64727 13.6969 9.64727 13.3313V9.75942C9.64727 9.3938 9.33789 9.11255 9.00039 9.11255Z"
                  fill=""
                />
                <path
                  d="M11.2502 9.67504C10.8846 9.64692 10.6033 9.90004 10.5752 10.2657L10.4064 12.7407C10.3783 13.0782 10.6314 13.3875 10.9971 13.4157C11.0252 13.4157 11.0252 13.4157 11.0533 13.4157C11.3908 13.4157 11.6721 13.1625 11.6721 12.825L11.8408 10.35C11.8408 9.98442 11.5877 9.70317 11.2502 9.67504Z"
                  fill=""
                />
                <path
                  d="M6.72245 9.67504C6.38495 9.70317 6.1037 10.0125 6.13182 10.35L6.3287 12.825C6.35683 13.1625 6.63808 13.4157 6.94745 13.4157C6.97558 13.4157 6.97558 13.4157 7.0037 13.4157C7.3412 13.3875 7.62245 13.0782 7.59433 12.7407L7.39745 10.2657C7.39745 9.90004 7.08808 9.64692 6.72245 9.67504Z"
                  fill=""
                />
              </svg>
            </button>
          </div>
        </div>
      ));
    }
    return <div className="p-4 text-center">No clients found.</div>;
  };

  const renderEditForm = () => {
    if (!editingClient) return null;

    return (
      <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-99999">
        <div className="bg-white rounded-lg p-6 shadow-lg w-full max-w-lg">
          <h3 className="text-xl font-medium text-black mb-4">Edit Client</h3>
          <form onSubmit={handleSubmit}>
            <div className="grid grid-cols-2 gap-4">
              <div className="mb-4">
                <label className="block mb-2 text-black">First Name</label>
                <input
                  type="text"
                  name="firstName"
                  value={formData?.firstName ?? ''}
                  onChange={handleInputChange}
                  className="w-full border rounded py-2 px-3"
                />
                {errors.firstName && (
                  <p className="text-red-500 text-sm">{errors.firstName}</p>
                )}
              </div>
              <div className="mb-4">
                <label className="block mb-2 text-black">Last Name</label>
                <input
                  type="text"
                  name="lastName"
                  value={formData?.lastName ?? ''}
                  onChange={handleInputChange}
                  className="w-full border rounded py-2 px-3"
                />
                {errors.lastName && (
                  <p className="text-red-500 text-sm">{errors.lastName}</p>
                )}
              </div>
              <div className="mb-4">
                <label className="block mb-2 text-black">Email</label>
                <input
                  type="email"
                  name="email"
                  value={formData?.email ?? ''}
                  onChange={handleInputChange}
                  className="w-full border rounded py-2 px-3"
                />
                {errors.email && (
                  <p className="text-red-500 text-sm">{errors.email}</p>
                )}
              </div>
              <div className="mb-4">
                <label className="block mb-2 text-black">Phone</label>
                <input
                  type="text"
                  name="phone"
                  value={formData?.phone ?? ''}
                  onChange={handleInputChange}
                  className="w-full border rounded py-2 px-3"
                />
                {errors.phone && (
                  <p className="text-red-500 text-sm">{errors.phone}</p>
                )}
              </div>
              <div className="mb-4">
                <label className="block mb-2 text-black">Street</label>
                <input
                  type="text"
                  name="street"
                  value={formData?.address?.street ?? ''}
                  onChange={handleInputChange}
                  className="w-full border rounded py-2 px-3"
                />
                {errors.street && (
                  <p className="text-red-500 text-sm">{errors.street}</p>
                )}
              </div>
              <div className="mb-4">
                <label className="block mb-2 text-black">City</label>
                <input
                  type="text"
                  name="city"
                  value={formData?.address?.city ?? ''}
                  onChange={handleInputChange}
                  className="w-full border rounded py-2 px-3"
                />
                {errors.city && (
                  <p className="text-red-500 text-sm">{errors.city}</p>
                )}
              </div>
              <div className="mb-4">
                <label className="block mb-2 text-black">Country</label>
                <input
                  type="text"
                  name="country"
                  value={formData?.address?.country ?? ''}
                  onChange={handleInputChange}
                  className="w-full border rounded py-2 px-3"
                />
                {errors.country && (
                  <p className="text-red-500 text-sm">{errors.country}</p>
                )}
              </div>
            </div>
            <div className="flex justify-end space-x-4">
              <button
                type="button"
                className="bg-gray-300 py-2 px-4 rounded"
                onClick={handleFormClose}
              >
                Cancel
              </button>
              <button
                type="submit"
                className="bg-blue-500 text-white py-2 px-4 rounded"
              >
                Save
              </button>
            </div>
          </form>
        </div>
      </div>
    );
  };

  return (
    <div className="rounded-sm border border-stroke bg-white shadow-default dark:border-strokedark dark:bg-boxdark">
      <div className="flex justify-between items-center py-4 px-4 md:px-6 xl:px-7.5">
        <h4 className="text-xl font-semibold text-black dark:text-white">
          Clients
        </h4>
        <input
          type="text"
          placeholder="Search..."
          value={searchTerm}
          onChange={(e) => setSearchTerm(e.target.value)}
          className="ml-4 border rounded-2xl py-1 px-3 dark:text-white dark:bg-black"
        />
      </div>
      <div className="grid grid-cols-12 border-t border-stroke py-4.5 px-4 dark:border-strokedark sm:grid-cols-12 md:px-6 2xl:px-7.5">
        <div className="col-span-1 flex">
          <p className="font-medium">ID</p>
        </div>
        <div className="col-span-2 flex items-center">
          <p className="font-medium">Client Name</p>
        </div>
        <div className="col-span-3 flex items-center place-content-center">
          <p className="font-medium">Email</p>
        </div>
        <div className="col-span-2 flex items-center place-content-center">
          <p className="font-medium">Phone</p>
        </div>
        <div className="col-span-3 flex items-center place-content-center">
          <p className="font-medium">Address</p>
        </div>
        <div className="col-span-1 flex items-center place-content-center">
          <p className="font-medium">Actions</p>
        </div>
      </div>
      {renderClientData()}
      {renderEditForm()}
      {isConfirming && (
        <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-99999">
          <div className="bg-white rounded-lg p-6 shadow-lg max-w-sm w-full text-center">
            <h3 className="text-xl font-medium text-black mb-4">
              Confirm Save
            </h3>
            <p>Are you sure you want to save changes?</p>
            <div className="flex justify-center space-x-4 mt-4">
              <button
                className="bg-gray-300 py-2 px-4 rounded"
                onClick={() => setIsConfirming(false)}
              >
                Cancel
              </button>
              <button
                className="bg-blue-500 text-white py-2 px-4 rounded"
                onClick={handleConfirmSave}
              >
                Confirm
              </button>
            </div>
          </div>
        </div>
      )}
    </div>
  );
};

export default TableClients;
