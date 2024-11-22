import React, { useEffect, useState } from 'react';
import {
  getAllInventory,
  updateInventory,
} from '../../services/inventoryService.ts';
import { Inventory } from '../../types/Inventory.ts';

const TableInventory: React.FC = () => {
  const [inventory, setInventory] = useState<Inventory[]>([]);
  const [isLoading, setIsLoading] = useState<boolean>(true);
  const [searchTerm, setSearchTerm] = useState<string>('');
  const [isConfirming, setIsConfirming] = useState<boolean>(false);
  const [currentAction, setCurrentAction] = useState<string | null>(null);
  const [selectedInventory, setSelectedInventory] = useState<Inventory | null>(null);
  const [inputValue, setInputValue] = useState<string>('');

  let modalTitle: string;
  let modalOption: string;

  if (currentAction === 'addStock') {
    modalTitle = 'Modify Stock';
    modalOption = 'Stock'
  } else if (currentAction === 'changeLocation') {
    modalTitle = 'Change Location';
    modalOption = 'Location'
  } else {
    modalTitle = 'Change Status';
    modalOption = 'Status'
  }

  useEffect(() => {
    const fetchInventory = async () => {
      try {
        const data = await getAllInventory();
        setInventory(data);
      } catch (error) {
        console.error('Error fetching inventory:', error);
      } finally {
        setIsLoading(false);
      }
    };
    fetchInventory();
  }, []);

  const handleActionClick = (action: string, inventoryItem: Inventory) => {
    setCurrentAction(action);
    setSelectedInventory(inventoryItem);
    setInputValue('');
    setIsConfirming(true);
  };

  const handleConfirmSave = async () => {
    if (!selectedInventory) return;

    const updatedInventory = { ...selectedInventory };
    if (currentAction === 'addStock') {
      updatedInventory.quantity = selectedInventory.quantity + parseInt(inputValue || '0', 10);
    } else if (currentAction === 'changeLocation') {
      updatedInventory.location = inputValue;
    } else if (currentAction === 'toggleStatus') {
      updatedInventory.isActive = !selectedInventory.isActive;
    }

    try {
      const updatedItem = await updateInventory(selectedInventory.id, updatedInventory);
      setInventory((prev) =>
        prev.map((item) => (item.id === updatedItem.id ? updatedItem : item))
      );
      setIsConfirming(false);
    } catch (error) {
      console.error('Error updating inventory:', error);
    }
  };

  const renderInventoryData = () => {
    if (isLoading) {
      return <div className="p-4 text-center">Loading Inventory...</div>;
    }

    const filteredInventory = inventory.filter((inv) => {
      const regex = new RegExp(searchTerm, 'i');

      let matchesStatus = true;

      if (searchTerm.toLowerCase() === 'active') {
        matchesStatus = inv.isActive;
      } else if (searchTerm.toLowerCase() === 'inactive') {
        matchesStatus = !inv.isActive;
      }

      return (
        regex.test(inv.item.name) ||
        regex.test(inv.location) ||
        matchesStatus
      );
    }).sort((a, b) => {
      return (b.isActive ? 1 : 0) - (a.isActive ? 1 : 0);
    });

    if (filteredInventory.length > 0) {
      return filteredInventory.map((inventory) => (
        <div
          className="grid grid-cols-9 border-t border-stroke py-4.5 px-4 dark:border-strokedark sm:grid-cols-9 md:px-6 2xl:px-7.5"
          key={inventory.id}
        >
          <div className="col-span-1 flex items-center">
            <p className="text-sm text-black dark:text-white">{inventory.id}</p>
          </div>
          <div className="col-span-2 flex place-content-start">
            <p className="text-sm text-black dark:text-white">
              {inventory.item.name}
            </p>
          </div>
          <div className="col-span-1 flex item-center place-content-center">
            <p className="text-sm text-black dark:text-white">
              {inventory.item.id}
            </p>
          </div>
          <div className="col-span-2 flex place-content-center">
            <p className="text-sm text-black dark:text-white">
              {inventory.location || 'Update Location'}
            </p>
          </div>
          <div className="col-span-1 flex place-content-center">
            <p className="text-sm text-black dark:text-white">
              {inventory.quantity}
            </p>
          </div>
          <div className="col-span-1 flex place-content-center">
            <p className="text-sm text-black dark:text-white">
              {inventory.isActive ? 'Active' : 'Inactive'}
            </p>
          </div>
          <div className="col-span-1 flex place-content-center space-x-3.5">
            <button
              className="hover:text-primary"
              title="Edit Stock"
              onClick={() => handleActionClick('addStock', inventory)}
            >
              <svg
                xmlns="http://www.w3.org/2000/svg"
                width="18"
                height="18"
                viewBox="0 0 24 24"
                stroke="currentColor"
                strokeWidth="2"
                strokeLinecap="round"
                strokeLinejoin="round"
                className="fill-current"
              >
                <path d="M22 19a2 2 0 0 1-2 2H4a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h5l2 3h9a2 2 0 0 1 2 2z"></path>
              </svg>
            </button>
            <button
              className="hover:text-primary"
              title="Change Location"
              onClick={() => handleActionClick('changeLocation', inventory)}
            >
              <svg
                xmlns="http://www.w3.org/2000/svg"
                width="18"
                height="18"
                viewBox="0 0 24 24"
                stroke="currentColor"
                strokeWidth="2"
                strokeLinecap="round"
                strokeLinejoin="round"
                fill="none"
              >
                <path d="M21 10c0 7-9 13-9 13s-9-6-9-13a9 9 0 0 1 18 0z"></path>
                <circle cx="12" cy="10" r="3"></circle>
              </svg>
            </button>
            <button
              className="hover:text-primary"
              title="Change Status"
              onClick={() => handleActionClick('toggleStatus', inventory)}
            >
              <svg
                xmlns="http://www.w3.org/2000/svg"
                width="18"
                height="18"
                viewBox="0 0 24 24"
                stroke="currentColor"
                strokeWidth="3"
                strokeLinecap="round"
                strokeLinejoin="round"
                className="fill-current"
              >
                <line x1="18" y1="20" x2="18" y2="10"></line>
                <line x1="12" y1="20" x2="12" y2="4"></line>
                <line x1="6" y1="20" x2="6" y2="14"></line>
              </svg>
            </button>
          </div>
        </div>
      ));
    }
    return <div className="p-4 text-center">No inventory items found.</div>;
  };

  return (
    <div className="rounded-sm border border-stroke bg-white shadow-default dark:border-strokedark dark:bg-boxdark">
      <div className="flex justify-between items-center py-4 px-4 md:px-6 xl:px-7.5">
        <h4 className="text-xl font-semibold text-black dark:text-white">
          Items
        </h4>
        <div className="flex justify-between items-center py-1 md:px-6 xl:px-7.5">
          <input
            type="text"
            placeholder="Search..."
            value={searchTerm}
            onChange={(e) => setSearchTerm(e.target.value)}
            className="ml-4 border rounded-2xl py-1 px-3 dark:text-white dark:bg-black"
          />
        </div>
      </div>
      <div className="grid grid-cols-9 border-t border-stroke py-4.5 px-4 dark:border-strokedark sm:grid-cols-9 md:px-6 2xl:px-7.5">
        <div className="col-span-1 flex items-center">
          <p className="font-medium">ID</p>
        </div>
        <div className="col-span-2 flex ">
          <p className="font-medium">Product Name</p>
        </div>
        <div className="col-span-1 flex place-content-center">
          <p className="font-medium">Product ID</p>
        </div>
        <div className="col-span-2 flex place-content-center">
          <p className="font-medium">Location</p>
        </div>
        <div className="col-span-1 flex place-content-center">
          <p className="font-medium">Quantity</p>
        </div>
        <div className="col-span-1 flex place-content-center">
          <p className="font-medium">Status</p>
        </div>
        <div className="col-span-1 flex place-content-center">
          <p className="font-medium">Actions</p>
        </div>
      </div>
      {renderInventoryData()}

      {isConfirming && (
        <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center">
          <div className="bg-white rounded-lg p-6 shadow-lg max-w-sm w-full text-center">
            <h3 className="text-xl font-medium text-black mb-4">
              {modalTitle}
            </h3>
            {(currentAction === 'addStock' ||
              currentAction === 'changeLocation') && (
              <input
                type="text"
                value={inputValue}
                onChange={(e) => setInputValue(e.target.value)}
                placeholder={
                  currentAction === 'addStock'
                    ? 'Enter stock quantity'
                    : 'Enter new location'
                }
                className="border rounded w-full py-2 px-3 mb-4"
              />
            )}
            <p>Are you sure you want to update {modalOption}?</p>
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

export default TableInventory;
