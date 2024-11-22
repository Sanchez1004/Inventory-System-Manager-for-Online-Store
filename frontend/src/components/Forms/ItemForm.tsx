import React, { useState } from 'react';
import { Item } from '../../types/item.ts';

type ItemFormProps = {
  initialData: Item | null;
  onSubmit: (formData: Item) => void;
  onClose: () => void;
};

const ItemForm: React.FC<ItemFormProps> = ({ initialData, onSubmit, onClose }) => {
  const emptyItem: Item = {
    id: 0,
    name: '',
    description: '',
    price: 0,
    category: '',
    photo: '',
  };

  const [formData, setFormData] = useState<Item>(initialData || emptyItem);
  const [errors, setErrors] = useState<{ [key: string]: string }>({});

  const handleInputChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
    const { name, value } = e.target;

    setFormData({
      ...formData,
      [name]: name === 'price' ? parseFloat(value) || 0 : value,
    });
  };

  const validateForm = (): boolean => {
    let formErrors: { [key: string]: string } = {};

    if (!formData.name) formErrors.name = 'Name is required.';
    if (!formData.price || formData.price <= 0) formErrors.price = 'Price must be greater than 0.';
    if (!formData.category) formErrors.category = 'Category is required.';

    setErrors(formErrors);
    return Object.keys(formErrors).length === 0;
  };

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();

    if (!validateForm()) return;

    onSubmit(formData);
  };

  return (
    <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-99999">
      <div className="bg-white rounded-lg p-6 shadow-lg w-full max-w-lg">
        <h3 className="text-black font-medium text-black mb-4 dark:text-white">
          {initialData ? 'Edit Item' : 'Add Item'}
        </h3>
        <form onSubmit={handleSubmit}>
          <div className="grid grid-cols-2 gap-4">
            <div className="mb-4">
              <label className="block mb-2 text-black">Name</label>
              <input
                type="text"
                name="name"
                value={formData.name}
                onChange={handleInputChange}
                className="w-full border rounded py-2 px-3"
              />
              {errors.name && (
                <p className="text-red-500 text-sm">{errors.name}</p>
              )}
            </div>
            <div className="mb-1">
              <label className="block mb-2 text-black">Price</label>
              <input
                type="number"
                name="price"
                value={formData.price}
                onChange={handleInputChange}
                className="w-full border rounded py-2 px-3"
              />
              {errors.price && (
                <p className="text-red-500 text-sm">{errors.price}</p>
              )}
            </div>
            <div className="mb-1">
              <label className="block mb-2 text-black">Description</label>
              <textarea
                name="description"
                value={formData.description}
                onChange={handleInputChange}
                className="w-full border rounded py-2 px-3 text-sm"
              />
            </div>
            <div className="mb-4">
              <label className="block mb-2 text-black">Category</label>
              <input
                type="text"
                name="category"
                value={formData.category}
                onChange={handleInputChange}
                className="w-full border rounded py-2 px-3 text-sm"
              />
              {errors.category && (
                <p className="text-red-500 text-sm">{errors.category}</p>
              )}
            </div>
            <div className="mb-4">
              <label className="block mb-2 text-black">Photo URL</label>
              <input
                type="text"
                name="photo"
                value={formData.photo}
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

export default ItemForm;
