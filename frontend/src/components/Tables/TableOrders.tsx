import React, { useEffect, useState } from 'react';
import { CustomerOrder } from '../../types/customerOrder.ts';
import { getAllOrders, deleteOrder } from '../../services/orderService.ts';

const TableOrders: React.FC = () => {
  const [orders, setOrders] = useState<CustomerOrder[]>([]);
  const [isLoading, setIsLoading] = useState<boolean>(true);
  const [searchTerm, setSearchTerm] = useState<string>('');
  const [selectedOrder, setSelectedOrder] = useState<CustomerOrder | null>(null);

  useEffect(() => {
    const fetchOrders = async () => {
      try {
        const data = await getAllOrders();
        console.log(data);
        setOrders(data);
      } catch (error) {
        console.error('Error fetching orders:', error);
      } finally {
        setIsLoading(false);
      }
    };
    fetchOrders();
  }, []);

  const handleDetailsClick = (order: CustomerOrder) => {
    setSelectedOrder(order);
  };

  const handleDeleteClick = async (orderId: number) => {
    const confirmDelete = window.confirm('Are you sure you want to delete this order?');
    if (confirmDelete) {
      try {
        await deleteOrder(orderId);
        setOrders((prevOrders) => prevOrders.filter((order) => order.id !== orderId));
      } catch (error) {
        console.error('Error deleting order:', error);
      }
    }
  };

  const renderOrdersData = () => {
    if (isLoading) {
      return <div className="p-4 text-center">Loading Orders...</div>;
    }

    const filteredOrders = orders.filter((order) => {
      const regex = new RegExp(searchTerm, 'i');
      return (
        regex.test(order.id.toString()) ||
        regex.test(order.clientFirstName) ||
        regex.test(order.clientLastName)
      );
    });

    if (filteredOrders.length > 0) {
      return filteredOrders.map((order) => (
        <div
          className="grid grid-cols-7 items-center border-t border-stroke py-4.5 px-4 dark:border-strokedark md:px-7 2xl:px-7.5"
          key={order.id}
        >
          <div className="col-span-1 flex items-center">
            <p className="text-sm text-black dark:text-white">{order.id}</p>
          </div>
          <div className="col-span-1 flex items-center place-content-center">
            <p className="text-sm text-black dark:text-white">{order.clientId}</p>
          </div>
          <div className="col-span-2 flex place-content-center">
            <p className="text-sm text-black dark:text-white">
              {new Date(order.orderDate).toLocaleDateString('en-US', {
                weekday: 'long',
                year: 'numeric',
                month: 'long',
                day: 'numeric',
              })}
            </p>
          </div>
          <div className="col-span-1 flex place-content-center">
            <p className="text-sm text-black dark:text-white">
              ${order.totalAmount.toFixed(2)}
            </p>
          </div>
          <div className="col-span-2 flex items-center justify-center space-x-3.5">
            <button
              className="hover:text-primary"
              title="View Details"
              onClick={() => handleDetailsClick(order)}
            >
              Details
            </button>
            <button
              className="hover:text-primary"
              title="Delete Order"
              onClick={() => handleDeleteClick(order.id)}
            >
              Delete
            </button>
          </div>
        </div>
      ));
    }
    return <div className="p-4 text-center">No orders found.</div>;
  };

  return (
    <div className="rounded-sm border border-stroke bg-white shadow-default dark:border-strokedark dark:bg-boxdark">
      <div className="flex justify-between items-center py-4 px-4 md:px-6 xl:px-7.5">
        <h4 className="text-xl font-semibold text-black dark:text-white">
          Orders
        </h4>
        <input
          type="text"
          placeholder="Search..."
          value={searchTerm}
          onChange={(e) => setSearchTerm(e.target.value)}
          className="ml-4 border rounded-2xl py-1 px-3 dark:text-white dark:bg-black"
        />
      </div>
      <div className="grid grid-cols-7 border-t border-stroke py-4.5 px-4 dark:border-strokedark md:px-7 2xl:px-7.5">
        <div className="col-span-1 flex items-center">
          <p className="font-medium">Order ID</p>
        </div>
        <div className="col-span-1 flex items-center place-content-center">
          <p className="font-medium">Client ID</p>
        </div>
        <div className="col-span-2 flex place-content-center">
          <p className="font-medium">Date</p>
        </div>
        <div className="col-span-1 flex place-content-center">
          <p className="font-medium">Total</p>
        </div>
        <div className="col-span-2 flex place-content-center">
          <p className="font-medium">Actions</p>
        </div>
      </div>
      {renderOrdersData()}
      {selectedOrder && (
        <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-99999">
          <div className="col-span-3 flex items-center justify-between p-6 bg-white rounded-lg shadow dark:bg-boxdark">
            <div className="flex-row items-center space-x-4">
              <p className="text-2xl font-semibold text-gray-800 dark:text-white">
                Last Order
              </p>
              <div className="mt-2 grid grid-cols-8 md:grid-cols-8 md:gap-2 xl:grid-cols-8 2xl:gap-7.5">
                <p className="col-span-2 text-sm font-medium text-gray-500 dark:text-white">
                  Client ID: {selectedOrder.clientId}{' '}
                </p>
                <p className="col-span-4 text-sm font-medium text-gray-500 dark:text-white">
                  Client Name: {selectedOrder.clientFirstName}{' '}
                  {selectedOrder.clientLastName}
                </p>
                <p className="col-span-2 text-sm font-medium text-gray-500 dark:text-white">
                  Phone: {selectedOrder.clientPhone}{' '}
                </p>
                <p className="col-span-2 text-sm font-medium text-gray-500 dark:text-white">
                  Order ID : {selectedOrder.id}
                </p>
                <p className="col-span-4 text-sm font-medium text-gray-500 dark:text-white">
                  Address: {selectedOrder.clientCountry},
                  {selectedOrder.clientCity},{selectedOrder.clientStreet}
                </p>
                <p className="col-span-2 text-sm font-medium text-gray-500 dark:text-white">
                  Date: {''}
                  {new Date(selectedOrder.orderDate).toLocaleDateString(
                    'en-US',
                    {
                      weekday: 'long',
                      year: 'numeric',
                      month: 'long',
                      day: 'numeric',
                    },
                  )}
                </p>
                <div className="col-span-full text-sm font-medium text-gray-500 dark:text-white mt-2">
                  <p className="col-span-4 text-xl font-bold text-gray-500 dark:text-white">
                    Items:
                  </p>
                  <div className="overflow-y-auto max-h-64 border rounded-lg">
                    <div className="grid grid-cols-10 border-t border-stroke py-4.5 px-4 dark:border-strokedark sm:grid-cols-10 md:px-6 2xl:px-7.5">
                      <div className="col-span-1 flex">
                        <p className="font-medium">ID</p>
                      </div>
                      <div className="col-span-3 flex items-center">
                        <p className="font-medium">Inventory Name</p>
                      </div>
                      <div className="col-span-1 flex items-center place-content-center">
                        <p className="font-medium">Quantity</p>
                      </div>
                      <div className="col-span-2 flex items-center place-content-center">
                        <p className="font-medium">Unit Price</p>
                      </div>
                      <div className="col-span-3 flex items-center place-content-center">
                        <p className="font-medium">Subtotal</p>
                      </div>
                    </div>

                    {selectedOrder.orderItems.length > 0 ? (
                      selectedOrder.orderItems.map((item) => (
                        <div
                          key={item.id}
                          className="grid grid-cols-10 items-center border-t border-stroke py-4.5 px-4 dark:border-strokedark sm:grid-cols-10 md:px-6 2xl:px-7.5"
                        >
                          <div className="col-span-1 flex">
                            <p className="text-sm text-black dark:text-white">
                              {item.id}
                            </p>
                          </div>
                          <div className="col-span-3 flex">
                            <p className="text-sm text-black dark:text-white">
                              {item.inventoryName}
                            </p>
                          </div>
                          <div className="col-span-1 flex place-content-center">
                            <p className="text-sm text-black dark:text-white">
                              {item.quantity}
                            </p>
                          </div>
                          <div className="col-span-2 flex place-content-center">
                            <p className="text-sm text-black dark:text-white">
                              ${item.unitPrice.toFixed(2)}
                            </p>
                          </div>
                          <div className="col-span-3 flex place-content-center">
                            <p className="text-sm text-black dark:text-white">
                              ${(
                                item.subtotal ?? item.unitPrice * item.quantity
                              ).toFixed(2)}
                            </p>
                          </div>
                        </div>
                      ))
                    ) : (
                      <div className="col-span-full flex items-center justify-center py-4.5">
                        <p className="text-sm text-gray-500">
                          No order items found.
                        </p>
                      </div>
                    )}
                  </div>
                </div>
                <p className="col-span-7 place-content-center text-xl font-bold text-gray-500 dark:text-white">
                  Total Amount: ${(selectedOrder.totalAmount).toFixed(2)}
                </p>
                <button
                  className="place-content-center mt-4 bg-gray-300 py-2 px-4 rounded"
                  onClick={() => setSelectedOrder(null)}
                >
                  Close
                </button>
              </div>
            </div>
          </div>
        </div>
      )}
    </div>
  );
  }
;

export default TableOrders;
