import React, { createContext, useContext, useReducer, useEffect } from "react";
import { fetchInitialCartItems } from "./cartUtil";
import { useAuthUser, useAuthHeader } from "react-auth-kit";

// import cartReducer from "./CartReducer";
const CartContext = createContext();

function cartReducer(state, action) {
  switch (action.type) {
    case "ADD_TO_CART":
      if (state.some((item) => item.id === action.payload.id)) {
        return state;
      } else {
        return [...state, action.payload];
      }
    case "REMOVE_FROM_CART":
      return state.filter((item) => item.id !== action.payload.productId);
    case "UPDATE_QUANTITY":
      return state.map((item) =>
        item.id === action.payload.productId
          ? {
              ...item,
              quantity: parseInt(item.quantity) + action.payload.quantity,
            }
          : item
      );
    case "SET_CART_ITEMS":
      return action.payload;
    default:
      return state;
  }
}

export const CartProvider = ({ children }) => {
  const [cartItems, dispatch] = useReducer(cartReducer, []);
  // const { authState } = useAuthUser();
  const auth = useAuthUser();
  const autha = useAuthHeader();

  const userToken = autha().slice(6);
  // const userEmail = auth().email;
  useEffect(() => {
    // Fetch initial cart items from the backend when the component mounts
    fetchInitialCartItems(dispatch, userToken);
  }, [userToken]);

  return (
    <CartContext.Provider value={{ cartItems, dispatch }}>
      {children}
    </CartContext.Provider>
  );
};

export const useCart = () => {
  const context = useContext(CartContext);
  if (!context) {
    throw new Error("useCart must be used within a CartProvider");
  }
  return context;
};
