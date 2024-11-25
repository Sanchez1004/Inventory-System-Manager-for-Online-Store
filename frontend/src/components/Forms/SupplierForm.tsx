import React, { useState } from 'react';
import { handleNestedChange } from '../../functions/handleNestedChange.tsx';
import { Supplier } from '../../types/supplier.ts';

type SupplierFormProps = {
  initialData: Supplier | null;
  onSubmit: (formData: Supplier) => void;
  onClose: () => void;
};

const SupplierForm: React.FC<SupplierFormProps> = ({ initialData, onSubmit, onClose }) => {
  const emptySupplier: Supplier = {
    id: 0,
    firstName: '',
    lastName: '',
    email: '',
    companyName: '',
    address: { street: '', city: '', country: '' },
  }

  const [formData, setFormData] = useState<Supplier | null>(initialData || emptySupplier);
  const [errors, setErrors] = useState<{ [key: string]: string }>({});

  const nestedKeys: string[] = ['street', 'city', 'country'];
  const handleInputChange = handleNestedChange(setFormData, 'address', nestedKeys);


  const validateForm = (): boolean => {
    let formErrors: { [key: string]: string } = {};

    if (!formData?.firstName) formErrors.firstName = 'First name is required.';
    if (!formData?.lastName) formErrors.lastName = 'Last name is required.';
    if (!formData?.email) formErrors.email = 'Email is required.';
    if (!formData?.companyName) formErrors.companyName = 'Company name is required.';
    if (!formData?.address?.country) formErrors.country = 'Country is required is required.';

    setErrors(formErrors);
    return Object.keys(formErrors).length === 0;
  };

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();

    if (!validateForm()) return;

    if (formData) {
      onSubmit(formData);
    }
  };

  return (
    <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-99999">
      <div className="bg-white rounded-lg p-6 shadow-lg w-full max-w-lg">
        <h3 className="text-xl font-medium text-black mb-4">
          {initialData ? 'Edit Supplier' : 'Add Supplier'}
        </h3>
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
              <label className="block mb-2 text-black">Company Name</label>
              <input
                type="text"
                name="companyName"
                value={formData?.companyName ?? ''}
                onChange={handleInputChange}
                className="w-full border rounded py-2 px-3"
              />
              {errors.companyName && (
                <p className="text-red-500 text-sm">{errors.companyName}</p>
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
            <div className="mb-4">
              <label className="block mb-2 text-black">City</label>
              <input
                type="text"
                name="city"
                value={formData?.address?.city ?? ''}
                onChange={handleInputChange}
                className="w-full border rounded py-2 px-3"
              />
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
            </div>
          </div>
          <div className="flex justify-end space-x-4">
            <button
              type="button"
              className="bg-gray-300 py-2 px-4 rounded"
              onClick={onClose}
            >
              Cancel
            </button>
            <button
              type="submit"
              className="bg-blue-500 text-white py-2 px-4 rounded"
            >
              {initialData ? 'Save' : 'Create'}
            </button>
          </div>
        </form>
      </div>
    </div>
  );
};

export default SupplierForm;
