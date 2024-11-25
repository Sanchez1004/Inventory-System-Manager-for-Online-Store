import React, { useEffect, useState } from 'react';
import CardDataStats from '../../components/CardDataStats';
import { getAllClients } from '../../services/clientService.ts';
import { getAllSuppliers } from '../../services/supplierService.ts';
import { getAllOrders } from '../../services/orderService.ts';
import { listItems } from '../../services/itemService.ts';
import { Client } from '../../types/client.ts';
import { Supplier } from '../../types/supplier.ts';
import { CustomerOrder } from '../../types/customerOrder.ts';
import { Item } from '../../types/item.ts';

type Data = {
  client: Client;
  supplier: Supplier;
  order: CustomerOrder;
  item: Item;
}
const emptyClient: Client = {
  id: 0,
  firstName: '',
  lastName: '',
  email: '',
  phone: '',
  password: '',
  role: '',
  address: {
    country: '',
    city: '',
    street: ''
  },
};

const emptySupplier: Supplier = {
  id: 0,
  firstName: '',
  lastName: '',
  companyName: '',
  email: '',
  role: '',
  address: {
    country: '',
    city: '',
    street: ''
  },
};

const emptyOrder: CustomerOrder = {
  id: 0,
  clientCity: '',
  clientCountry: '',
  clientStreet: '',
  clientEmail: '',
  clientFirstName: '',
  clientLastName: '',
  clientId: '',
  clientPhone: '',
  orderDate: '',
  status: '',
  orderItems: [],
  totalAmount: 0,
};

const emptyItem: Item = {
  id: 0,
  name: '',
  description: '',
  price: 0,
  category: '',
  photo: '',
};


const ECommerce: React.FC = () => {
  const [totals, setTotals] = useState<number[]>([]);
  const [lastData, setLastData] = useState<Data>({
    client: emptyClient,
    supplier: emptySupplier,
    order: emptyOrder,
    item: emptyItem,
  });
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);

  const fetchData = async () => {
    try {
      setLoading(true);

      const [clients, suppliers, orders, items] = await Promise.all([
        getAllClients(),
        getAllSuppliers(),
        getAllOrders(),
        listItems(),
      ]);
      console.log(orders);

      const totalClients = clients.length;
      const totalSuppliers = suppliers.length;
      const totalOrders = orders.length;
      const totalItems = items.length;

      const lastClient =
        clients.length > 0 ? clients[clients.length - 1] : emptyClient;
      const lastSupplier =
        suppliers.length > 0 ? suppliers[suppliers.length - 1] : emptySupplier;
      const lastOrder =
        orders.length > 0 ? orders[orders.length - 1] : emptyOrder;
      const lastItem = items.length > 0 ? items[items.length - 1] : emptyItem;

      setTotals([totalClients, totalSuppliers, totalOrders, totalItems]);
      setLastData({
        client: lastClient,
        supplier: lastSupplier,
        order: lastOrder,
        item: lastItem,
      });

      setError(null);
    } catch (err: any) {
      console.error('Error fetching data:', err);
      setError('Failed to fetch data. Please try again later.');
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchData();
  }, []);

  if (loading) {
    return <div>Loading...</div>;
  }

  if (error) {
    return <div>Error: {error}</div>;
  }

  return (
    <>
      <div className="grid grid-cols-4 gap-4 md:grid-cols-4 md:gap-6 xl:grid-cols-4 2xl:gap-7.5">
        <CardDataStats title="Total Users" total={totals[0].toString()} rate="">
          <svg
            className="fill-primary dark:fill-white"
            width="22"
            height="18"
            viewBox="0 0 22 18"
            fill="none"
            xmlns="http://www.w3.org/2000/svg"
          >
            <path
              d="M7.18418 8.03751C9.31543 8.03751 11.0686 6.35313 11.0686 4.25626C11.0686 2.15938 9.31543 0.475006 7.18418 0.475006C5.05293 0.475006 3.2998 2.15938 3.2998 4.25626C3.2998 6.35313 5.05293 8.03751 7.18418 8.03751ZM7.18418 2.05626C8.45605 2.05626 9.52168 3.05313 9.52168 4.29063C9.52168 5.52813 8.49043 6.52501 7.18418 6.52501C5.87793 6.52501 4.84668 5.52813 4.84668 4.29063C4.84668 3.05313 5.9123 2.05626 7.18418 2.05626Z"
              fill=""
            />
            <path
              d="M15.8124 9.6875C17.6687 9.6875 19.1468 8.24375 19.1468 6.42188C19.1468 4.6 17.6343 3.15625 15.8124 3.15625C13.9905 3.15625 12.478 4.6 12.478 6.42188C12.478 8.24375 13.9905 9.6875 15.8124 9.6875ZM15.8124 4.7375C16.8093 4.7375 17.5999 5.49375 17.5999 6.45625C17.5999 7.41875 16.8093 8.175 15.8124 8.175C14.8155 8.175 14.0249 7.41875 14.0249 6.45625C14.0249 5.49375 14.8155 4.7375 15.8124 4.7375Z"
              fill=""
            />
            <path
              d="M15.9843 10.0313H15.6749C14.6437 10.0313 13.6468 10.3406 12.7874 10.8563C11.8593 9.61876 10.3812 8.79376 8.73115 8.79376H5.67178C2.85303 8.82814 0.618652 11.0625 0.618652 13.8469V16.3219C0.618652 16.975 1.13428 17.4906 1.7874 17.4906H20.2468C20.8999 17.4906 21.4499 16.9406 21.4499 16.2875V15.4625C21.4155 12.4719 18.9749 10.0313 15.9843 10.0313ZM2.16553 15.9438V13.8469C2.16553 11.9219 3.74678 10.3406 5.67178 10.3406H8.73115C10.6562 10.3406 12.2374 11.9219 12.2374 13.8469V15.9438H2.16553V15.9438ZM19.8687 15.9438H13.7499V13.8469C13.7499 13.2969 13.6468 12.7469 13.4749 12.2313C14.0937 11.7844 14.8499 11.5781 15.6405 11.5781H15.9499C18.0812 11.5781 19.8343 13.3313 19.8343 15.4625V15.9438H19.8687Z"
              fill=""
            />
          </svg>
        </CardDataStats>
        <CardDataStats
          title="Total Suppliers"
          total={totals[1].toString()}
          rate=""
        >
          <svg
            xmlns="http://www.w3.org/2000/svg"
            width="20"
            height="22"
            viewBox="0 0 24 24"
            fill="none"
            strokeWidth="2"
            strokeLinecap="round"
            strokeLinejoin="round"
            className="stroke-primary dark:stroke-white"
          >
            <rect x="1" y="3" width="15" height="13"></rect>
            <polygon points="16 8 20 8 23 11 23 16 16 16 16 8"></polygon>
            <circle cx="5.5" cy="18.5" r="2.5"></circle>
            <circle cx="18.5" cy="18.5" r="2.5"></circle>
          </svg>
        </CardDataStats>
        <CardDataStats
          title="Total Orders"
          total={totals[2].toString()}
          rate=""
        >
          <svg
            className="fill-primary dark:fill-white"
            width="20"
            height="22"
            viewBox="0 0 20 22"
            fill="none"
            xmlns="http://www.w3.org/2000/svg"
          >
            <path
              d="M11.7531 16.4312C10.3781 16.4312 9.27808 17.5312 9.27808 18.9062C9.27808 20.2812 10.3781 21.3812 11.7531 21.3812C13.1281 21.3812 14.2281 20.2812 14.2281 18.9062C14.2281 17.5656 13.0937 16.4312 11.7531 16.4312ZM11.7531 19.8687C11.2375 19.8687 10.825 19.4562 10.825 18.9406C10.825 18.425 11.2375 18.0125 11.7531 18.0125C12.2687 18.0125 12.6812 18.425 12.6812 18.9406C12.6812 19.4219 12.2343 19.8687 11.7531 19.8687Z"
              fill=""
            />
            <path
              d="M5.22183 16.4312C3.84683 16.4312 2.74683 17.5312 2.74683 18.9062C2.74683 20.2812 3.84683 21.3812 5.22183 21.3812C6.59683 21.3812 7.69683 20.2812 7.69683 18.9062C7.69683 17.5656 6.56245 16.4312 5.22183 16.4312ZM5.22183 19.8687C4.7062 19.8687 4.2937 19.4562 4.2937 18.9406C4.2937 18.425 4.7062 18.0125 5.22183 18.0125C5.73745 18.0125 6.14995 18.425 6.14995 18.9406C6.14995 19.4219 5.73745 19.8687 5.22183 19.8687Z"
              fill=""
            />
            <path
              d="M19.0062 0.618744H17.15C16.325 0.618744 15.6031 1.23749 15.5 2.06249L14.95 6.01562H1.37185C1.0281 6.01562 0.684353 6.18749 0.443728 6.46249C0.237478 6.73749 0.134353 7.11562 0.237478 7.45937C0.237478 7.49374 0.237478 7.49374 0.237478 7.52812L2.36873 13.9562C2.50623 14.4375 2.9531 14.7812 3.46873 14.7812H12.9562C14.2281 14.7812 15.3281 13.8187 15.5 12.5469L16.9437 2.26874C16.9437 2.19999 17.0125 2.16562 17.0812 2.16562H18.9375C19.35 2.16562 19.7281 1.82187 19.7281 1.37499C19.7281 0.928119 19.4187 0.618744 19.0062 0.618744ZM14.0219 12.3062C13.9531 12.8219 13.5062 13.2 12.9906 13.2H3.7781L1.92185 7.56249H14.7094L14.0219 12.3062Z"
              fill=""
            />
          </svg>
        </CardDataStats>
        <CardDataStats title="Total Items" total={totals[3].toString()} rate="">
          <svg
            className="fill-primary dark:fill-white"
            width="22"
            height="22"
            viewBox="0 0 22 22"
            fill="none"
            xmlns="http://www.w3.org/2000/svg"
          >
            <path
              d="M21.1063 18.0469L19.3875 3.23126C19.2157 1.71876 17.9438 0.584381 16.3969 0.584381H5.56878C4.05628 0.584381 2.78441 1.71876 2.57816 3.23126L0.859406 18.0469C0.756281 18.9063 1.03128 19.7313 1.61566 20.3844C2.20003 21.0375 2.99066 21.3813 3.85003 21.3813H18.1157C18.975 21.3813 19.8 21.0031 20.35 20.3844C20.9 19.7656 21.2094 18.9063 21.1063 18.0469ZM19.2157 19.3531C18.9407 19.6625 18.5625 19.8344 18.15 19.8344H3.85003C3.43753 19.8344 3.05941 19.6625 2.78441 19.3531C2.50941 19.0438 2.37191 18.6313 2.44066 18.2188L4.12503 3.43751C4.19378 2.71563 4.81253 2.16563 5.56878 2.16563H16.4313C17.1532 2.16563 17.7719 2.71563 17.875 3.43751L19.5938 18.2531C19.6282 18.6656 19.4907 19.0438 19.2157 19.3531Z"
              fill=""
            />
            <path
              d="M14.3345 5.29375C13.922 5.39688 13.647 5.80938 13.7501 6.22188C13.7845 6.42813 13.8189 6.63438 13.8189 6.80625C13.8189 8.35313 12.547 9.625 11.0001 9.625C9.45327 9.625 8.1814 8.35313 8.1814 6.80625C8.1814 6.6 8.21577 6.42813 8.25015 6.22188C8.35327 5.80938 8.07827 5.39688 7.66577 5.29375C7.25327 5.19063 6.84077 5.46563 6.73765 5.87813C6.6689 6.1875 6.63452 6.49688 6.63452 6.80625C6.63452 9.2125 8.5939 11.1719 11.0001 11.1719C13.4064 11.1719 15.3658 9.2125 15.3658 6.80625C15.3658 6.49688 15.3314 6.1875 15.2626 5.87813C15.1595 5.46563 14.747 5.225 14.3345 5.29375Z"
              fill=""
            />
          </svg>
        </CardDataStats>
      </div>
      <p className="mt-10 text-2xl font-semibold text-gray-800 dark:text-white">
        Recent Updates
      </p>
      <div className="grid grid-cols-3 gap-4 md:grid-cols-3 md:gap-6 xl:grid-cols-3 2xl:gap-7.5 mt-2 sm:grid-cols-2">
        {/*Clients*/}
        <div className="flex items-center justify-between p-6 bg-white rounded-lg shadow dark:bg-boxdark">
          <div className="flex items-center space-x-4">
            <div>
              <h4 className="text-2xl font-semibold text-gray-800 dark:text-white">
                Last Client
              </h4>
              <p className="text-sm font-medium text-gray-500 dark:text-white">
                First Name: {lastData.client?.firstName}
              </p>
              <p className="text-sm font-medium text-gray-500 dark:text-white">
                Last Name : {lastData.client?.lastName}
              </p>
              <p className="text-sm font-medium text-gray-500 dark:text-white">
                Phone : {lastData.client?.phone}
              </p>
              <p className="text-sm font-medium text-gray-500 dark:text-white">
                Email: {lastData.client?.email}
              </p>
              <p className="text-sm font-medium text-gray-500 dark:text-white">
                Address : {lastData.client?.address?.country},{' '}
                {lastData.client?.address?.city},{' '}
                {lastData.client?.address?.street}
              </p>
            </div>
          </div>
        </div>
        {/*Clients*/}

        {/*Suppliers*/}
        <div className="flex items-center justify-between p-6 bg-white rounded-lg shadow dark:bg-boxdark">
          <div className="flex items-center space-x-4">
            <div>
              <h4 className="text-2xl font-semibold text-gray-800 dark:text-white">
                Last Supplier
              </h4>
              <p className="text-sm font-medium text-gray-500 dark:text-white">
                First Name: {lastData.supplier?.firstName}
              </p>
              <p className="text-sm font-medium text-gray-500 dark:text-white">
                Last Name : {lastData.supplier?.lastName}
              </p>
              <p className="text-sm font-medium text-gray-500 dark:text-white">
                Company : {lastData.supplier?.companyName}
              </p>
              <p className="text-sm font-medium text-gray-500 dark:text-white">
                Email : {lastData.supplier?.email}
              </p>
              <p className="text-sm font-medium text-gray-500 dark:text-white">
                Address : {lastData.supplier?.address?.country},{' '}
                {lastData.supplier?.address?.city},{' '}
                {lastData.supplier?.address?.street}
              </p>
            </div>
          </div>
        </div>
        {/*Suppliers*/}

        {/*Items*/}
        <div className="flex items-center justify-between p-6 bg-white rounded-lg shadow dark:bg-boxdark">
          <div className="flex items-center space-x-4">
            <div>
              <h4 className="text-2xl font-semibold text-gray-800 dark:text-white">
                Last Item
              </h4>
              <p className="text-sm font-medium text-gray-500 dark:text-white">
                ID: {lastData.item.id}
              </p>
              <p className="text-sm font-medium text-gray-500 dark:text-white">
                Name : {lastData.item.name}
              </p>

              <p className="text-sm font-medium text-gray-500 dark:text-white">
                Email : {lastData.item.price}
              </p>
              <p className="text-sm font-medium text-gray-500 dark:text-white">
                Category : {lastData.item.category}
              </p>
              <p className="text-sm font-medium text-gray-500 dark:text-white">
                Description : {lastData.item.description}
              </p>
            </div>
          </div>
        </div>
        {/*Items*/}

        {/*Orders*/}
        <div className="col-span-3 flex items-center justify-between p-6 bg-white rounded-lg shadow dark:bg-boxdark">
          <div className="flex-row items-center space-x-4">
            <p className="text-2xl font-semibold text-gray-800 dark:text-white">
              Last Order
            </p>
            <div className="mt-2 grid grid-cols-8 md:grid-cols-8 md:gap-2 xl:grid-cols-8 2xl:gap-7.5">
              <p className="col-span-2 text-sm font-medium text-gray-500 dark:text-white">
                Client ID: {lastData.order?.clientId}{' '}
              </p>
              <p className="col-span-4 text-sm font-medium text-gray-500 dark:text-white">
                Client Name: {lastData.order?.clientFirstName}{' '}
                {lastData.order.clientLastName}
              </p>
              <p className="col-span-2 text-sm font-medium text-gray-500 dark:text-white">
                Phone: {lastData.order?.clientPhone}{' '}
              </p>
              <p className="col-span-2 text-sm font-medium text-gray-500 dark:text-white">
                Order ID : {lastData.order?.id}
              </p>
              <p className="col-span-4 text-sm font-medium text-gray-500 dark:text-white">
                Address: {lastData.order?.clientCountry},
                {lastData.order?.clientCity},{lastData.order.clientStreet}
              </p>
              <p className="col-span-2 text-sm font-medium text-gray-500 dark:text-white">
                Date : {' '}
                {new Date(lastData.order?.orderDate).toLocaleDateString(
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

                  {lastData.order.orderItems.length > 0 ? (
                    lastData.order.orderItems.map((item) => (
                      <div
                        key={item.id}
                        className="grid grid-cols-10 items-center border-t border-stroke py-4.5 px-4 dark:border-strokedark sm:grid-cols-10 md:px-10 2xl:px-7.5"
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
              <p className="col-span-4 text-xl font-bold text-gray-500 dark:text-white">
                Total Amount: {(lastData.order.totalAmount).toFixed(2)}
              </p>
            </div>
          </div>
        </div>
        {/*Orders*/}
      </div>
    </>
  );
};

export default ECommerce;
