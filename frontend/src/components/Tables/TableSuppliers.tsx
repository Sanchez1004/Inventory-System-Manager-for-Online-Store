import React, { useEffect, useState } from 'react';
import { Supplier } from '../../types/supplier.ts';
import { createSupplier, deleteSupplier, getAllSuppliers, updateSupplier } from '../../services/supplierService.ts';
import SupplierForm from '../Forms/SupplierForm.tsx';

const TableSuppliers: React.FC = () => {
  const [supplier, setSupplier] = useState<Supplier[]>([]);
  const [isLoading, setIsLoading] = useState<boolean>(true);
  const [editingSupplier, setEditingSupplier] = useState<Supplier | null>(null);
  const [isConfirming, setIsConfirming] = useState(false);
  const [searchTerm, setSearchTerm] = useState<string>('');
  const [isFormOpen, setIsFormOpen] = useState<boolean>(false);
  const [pendingFormData, setPendingFormData] = useState<Supplier | null>(null);

  useEffect(() => {
    const fetchClients = async () => {
      try {
        const data = await getAllSuppliers();
        setSupplier(data);
      } catch (error) {
        console.error('Error al obtener los clientes:', error);
      } finally {
        setIsLoading(false);
      }
    };
    fetchClients();
  }, []);

  const handleEditClick = (supplier: Supplier) => {
    setEditingSupplier(supplier);
    setIsFormOpen(true);
  };

  const handleAddClick = () => {
    setEditingSupplier(null);
    setIsFormOpen(true);
  };

  const handleFormSubmit = (formData: Supplier) => {
    setPendingFormData(formData);
    setIsConfirming(true);
  };


  const handleFormClose = () => {
    setIsFormOpen(false);
    setEditingSupplier(null);
  };

  const handleConfirmSave = async () => {
    if (!pendingFormData) return;

    try {
      if (editingSupplier) {
        if (editingSupplier.id != null) {
          const updatedSupplier = await updateSupplier(editingSupplier.id, pendingFormData);
          setSupplier((prev) =>
            prev.map((supplier) =>
              supplier.id === updatedSupplier.id ? updatedSupplier : supplier
            )
          );
        }
        else {
          console.error('Error getting the supplier id.')
        }
      } else {
        const newSupplier = await createSupplier(pendingFormData);
        setSupplier((prev) => [...prev, newSupplier]);
      }
      setIsFormOpen(false);
    } catch (error) {
      console.error('Error saving the supplier:', error);
    } finally {
      setIsConfirming(false);
      setPendingFormData(null);
    }
  };

  const handleDeleteClick = async (supplierId: number | undefined) => {
    const confirmDelete = window.confirm(
      'Are you sure you want to delete this client?',
    );
    if (confirmDelete) {
      try {
        await deleteSupplier(supplierId);
        setSupplier((prevSuppliers) =>
          prevSuppliers.filter((supplier) => supplier.id !== supplierId),
        );
      } catch (error) {
        console.error('Error deleting supplier:', error);
      }
    }
  };

  const renderClientData = () => {
    if (isLoading) {
      return <div className="p-4 text-center">Loading clients...</div>;
    }

    const filteredClients = supplier.filter((supplier) => {
      const regex = new RegExp(searchTerm, 'i');
      return (
        regex.test(supplier.firstName ?? '') ||
        regex.test(supplier.lastName ?? '') ||
        regex.test(supplier.email) ||
        regex.test(supplier.companyName ?? '') ||
        regex.test(supplier.role ?? '') ||
        regex.test(supplier.address?.country ?? '') ||
        regex.test(supplier.address?.city ?? '') ||
        regex.test(supplier.address?.street ?? '')
      );
    });

    if (filteredClients.length > 0) {
      return filteredClients.map((supplier) => (
        <div
          className="grid grid-cols-12 border-t border-stroke py-4.5 px-4 dark:border-strokedark sm:grid-cols-12 md:px-6 2xl:px-7.5"
          key={supplier.id}
        >
          <div className="col-span-1 flex items-center">
            <p className="text-sm text-black dark:text-white">{supplier.id}</p>
          </div>
          <div className="col-span-2 flex items-center">
            <p className="text-sm text-black dark:text-white">
              {supplier.firstName} {supplier.lastName}
            </p>
          </div>
          <div className="col-span-3 flex items-center place-content-center">
            <p className="text-sm text-black dark:text-white">
              {supplier.email}
            </p>
          </div>
          <div className="col-span-2 flex items-center place-content-center">
            <p className="text-sm text-black dark:text-white">
              {supplier.companyName}
            </p>
          </div>
          <div className="col-span-3 flex items-center place-content-center text-wrap">
            <p className="text-sm text-black dark:text-white text-center">
              {supplier.address?.country}, {supplier.address?.city}, {supplier.address?.street}
            </p>
          </div>
          <div className="flex items-center space-x-3.5 place-content-center">
            <button
              className="hover:text-primary"
              title="Edit Supplier"
              onClick={() => handleEditClick(supplier)}
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
              title="Delete Supplier"
              onClick={() => handleDeleteClick(supplier.id)}
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
    return <div className="p-4 text-center">No suppliers found.</div>;
  };

  return (
    <div className="rounded-sm border border-stroke bg-white shadow-default dark:border-strokedark dark:bg-boxdark">
      <div className="flex justify-between items-center py-4 px-4 md:px-6 xl:px-7.5">
        <h4 className="text-xl font-semibold text-black dark:text-white">
          Suppliers
        </h4>
        <div className="flex justify-between items-center py-1 md:px-6 xl:px-7.5">
          <button
            className="inline-flex items-center justify-center gap-2.5 rounded-md bg-black py-1 px-2 text-center font-medium text-white hover:bg-opacity-90"
            onClick={handleAddClick}
          >
            <svg
              xmlns="http://www.w3.org/2000/svg"
              width="16"
              height="16"
              viewBox="0 0 24 24"
              stroke="currentColor"
              strokeWidth="2"
              strokeLinecap="round"
              strokeLinejoin="round"
              className="fill-current"
            >
              <path d="M16 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2"></path>
              <circle cx="8.5" cy="7" r="4"></circle>
              <line x1="20" y1="8" x2="20" y2="14"></line>
              <line x1="23" y1="11" x2="17" y2="11"></line>
            </svg>
            Add
          </button>
          <input
            type="text"
            placeholder="Search..."
            value={searchTerm}
            onChange={(e) => setSearchTerm(e.target.value)}
            className="ml-4 border rounded-2xl py-1 px-3 dark:text-white dark:bg-black"
          />
        </div>
      </div>
      <div className="grid grid-cols-12 border-t border-stroke py-4.5 px-4 dark:border-strokedark sm:grid-cols-12 md:px-6 2xl:px-7.5">
        <div className="col-span-1 flex items-center">
          <p className="font-medium">ID</p>
        </div>
        <div className="col-span-2 flex items-center">
          <p className="font-medium">Client Name</p>
        </div>
        <div className="col-span-3 flex items-center place-content-center">
          <p className="font-medium">Email</p>
        </div>
        <div className="col-span-2 flex items-center place-content-center">
          <p className="font-medium">Company</p>
        </div>
        <div className="col-span-3 flex items-center place-content-center">
          <p className="font-medium">Address</p>
        </div>
        <div className="col-span-1 flex items-center place-content-center">
          <p className="font-medium">Actions</p>
        </div>
      </div>
      {renderClientData()}
      {isFormOpen && (
        <SupplierForm
          initialData={editingSupplier}
          onSubmit={handleFormSubmit}
          onClose={handleFormClose}
        />
      )}
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

export default TableSuppliers;
