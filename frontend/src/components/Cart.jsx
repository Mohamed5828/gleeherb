import { Fragment, useEffect, useState } from "react";
import { Dialog, Transition } from "@headlessui/react";
import { XMarkIcon } from "@heroicons/react/24/outline";
import { useAuthHeader, useIsAuthenticated } from "react-auth-kit";
import { useCart } from "../tools/CartContext";
import { useNavigate } from "react-router-dom";

import {
  handleDecrement,
  handleIncrement,
  handleRemove,
} from "../tools/CartHandlers";
import { fetchData } from "../tools/DataFetching";

export default function Cart({ isCartOpen, toggleCart }) {
  const { cartItems, dispatch } = useCart();
  const [totalPrice, setTotalPrice] = useState(0);
  const autha = useAuthHeader();
  const userToken = autha().slice(6);
  const isAuth = useIsAuthenticated();
  const navigate = useNavigate();

  useEffect(() => {
    setTotalPrice(() => {
      return cartItems.reduce((accumulator, item) => {
        return accumulator + item.priceEg * item.quantity;
      }, 0);
    });
  }, [cartItems]);
  async function handlePayment() {
    try {
      const response = await fetchData("api/user-address", userToken);
      if (response == null) {
        navigate("/address");
      }
    } catch (error) {
      console.error("Error fetching data:", error);
      navigate("/address");
    }
    isAuth() ? navigate("/checkout") : navigate("/login");
  }
  return (
    <Transition.Root show={isCartOpen} as={Fragment}>
      <Dialog as="div" className="relative z-10" onClose={toggleCart}>
        <Transition.Child
          as={Fragment}
          enter="ease-in-out duration-500"
          enterFrom="opacity-0"
          enterTo="opacity-100"
          leave="ease-in-out duration-500"
          leaveFrom="opacity-100"
          leaveTo="opacity-0"
        >
          <div className="fixed inset-0 bg-gray-500 bg-opacity-75 transition-opacity" />
        </Transition.Child>

        <div className="fixed inset-0 overflow-hidden">
          <div className="absolute inset-0 overflow-hidden">
            <div className="pointer-events-none fixed inset-y-0 right-0 flex max-w-full pl-10">
              <Transition.Child
                as={Fragment}
                enter="transform transition ease-in-out duration-500 sm:duration-700"
                enterFrom="translate-x-full"
                enterTo="translate-x-0"
                leave="transform transition ease-in-out duration-500 sm:duration-700"
                leaveFrom="translate-x-0"
                leaveTo="translate-x-full"
              >
                <Dialog.Panel className="pointer-events-auto w-screen max-w-md">
                  <div className="flex h-full flex-col overflow-y-scroll bg-white shadow-xl">
                    <div className="flex-1 overflow-y-auto px-4 py-6 sm:px-6">
                      <div className="flex items-start justify-between">
                        <Dialog.Title className="text-lg font-medium text-gray-900">
                          Shopping cart
                        </Dialog.Title>
                        <div className="ml-3 flex h-7 items-center">
                          <button
                            type="button"
                            className="-m-2 p-2 text-gray-400 hover:text-gray-500"
                            onClick={toggleCart}
                          >
                            <span className="sr-only">Close panel</span>
                            <XMarkIcon className="h-6 w-6" aria-hidden="true" />
                          </button>
                        </div>
                      </div>

                      <div className="mt-8">
                        <div className="flow-root">
                          <ul
                            role="list"
                            className="-my-6 divide-y divide-gray-200"
                          >
                            {cartItems != null
                              ? cartItems.map((product) => (
                                  <li key={product.id} className="flex py-6">
                                    <div className="h-24 w-24 flex-shrink-0 overflow-hidden rounded-md border border-gray-200">
                                      <img
                                        src={product.firstImage}
                                        alt={product.title}
                                        className="h-full w-full object-cover object-center"
                                      />
                                    </div>

                                    <div className="ml-4 flex flex-1 flex-col">
                                      <div>
                                        <div className="flex justify-between text-base font-medium text-gray-900">
                                          <h3>
                                            <a
                                              href={
                                                product.id < 100
                                                  ? `/product/${product.id}`
                                                  : "/"
                                              }
                                            >
                                              {product.title}
                                            </a>
                                          </h3>
                                          <p className="ml-4">
                                            {product.priceEg} L.E
                                          </p>
                                        </div>
                                        <p className="mt-1 text-sm text-gray-500">
                                          {product.weight} gm
                                        </p>
                                      </div>
                                      <div className="flex flex-1 items-end justify-between text-sm">
                                        <svg
                                          xmlns="http://www.w3.org/2000/svg"
                                          fill="none"
                                          viewBox="0 0 24 24"
                                          strokeWidth={1.5}
                                          stroke="currentColor"
                                          className="w-6 h-6 hover:text-gray-500"
                                          onClick={() =>
                                            handleIncrement(
                                              dispatch,
                                              userToken,
                                              product.id
                                            )
                                          }
                                        >
                                          <path
                                            strokeLinecap="round"
                                            strokeLinejoin="round"
                                            className="font-medium text-black-800 "
                                            d="M4.5 15.75l7.5-7.5 7.5 7.5"
                                          />
                                        </svg>

                                        <p className="text-gray-500">
                                          Qty {product.quantity}
                                        </p>
                                        <svg
                                          xmlns="http://www.w3.org/2000/svg"
                                          fill="none"
                                          viewBox="0 0 24 24"
                                          strokeWidth={1.5}
                                          stroke="currentColor"
                                          className="w-6 h-6 hover:text-gray-500"
                                          onClick={() =>
                                            handleDecrement(
                                              dispatch,
                                              product.id,
                                              userToken,
                                              product.quantity
                                            )
                                          }
                                        >
                                          <path
                                            strokeLinecap="round"
                                            strokeLinejoin="round"
                                            d="M19.5 8.25l-7.5 7.5-7.5-7.5"
                                            className="font-medium text-black-800 "
                                          />
                                        </svg>

                                        <div className="flex">
                                          <button
                                            type="button"
                                            className="font-medium text-indigo-600 hover:text-indigo-500"
                                            onClick={() =>
                                              handleRemove(
                                                dispatch,
                                                userToken,
                                                product.id
                                              )
                                            }
                                          >
                                            Remove
                                          </button>
                                        </div>
                                      </div>
                                    </div>
                                  </li>
                                ))
                              : ""}
                          </ul>
                        </div>
                      </div>
                    </div>

                    <div className="border-t border-gray-200 px-4 py-6 sm:px-6">
                      <div className="flex justify-between text-base font-medium text-gray-900">
                        <p>Subtotal</p>
                        <p>{totalPrice.toFixed(2)} L.E</p>
                      </div>
                      <p className="mt-0.5 text-sm text-gray-500">
                        Shipping and taxes calculated at checkout.
                      </p>
                      <div className="mt-6">
                        <a
                          onClick={() => {
                            handlePayment();
                            toggleCart();
                          }}
                          className="flex items-center justify-center rounded-md border border-transparent bg-indigo-600 px-6 py-3 text-base font-medium text-white shadow-sm hover:bg-indigo-700 cursor-pointer"
                        >
                          Checkout
                        </a>
                      </div>
                      <div className="mt-6 flex justify-center text-center text-sm text-gray-500">
                        <p>
                          or{" "}
                          <button
                            type="button"
                            className="font-medium text-indigo-600 hover:text-indigo-500"
                            onClick={toggleCart}
                          >
                            Continue Shopping
                            <span aria-hidden="true"> &rarr;</span>
                          </button>
                        </p>
                      </div>
                    </div>
                  </div>
                </Dialog.Panel>
              </Transition.Child>
            </div>
          </div>
        </div>
      </Dialog>
    </Transition.Root>
  );
}
