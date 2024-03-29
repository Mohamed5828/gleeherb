import { useAuthUser } from "react-auth-kit";
import { fetchData } from "./DataFetching";
import postData from "./DataPosting";

export async function fetchInitialCartItems(dispatch, userToken) {
  if (!userToken) {
    const cartItems = JSON.parse(localStorage.getItem("cartItems")) || [];
    dispatch({ type: "SET_CART_ITEMS", payload: cartItems });
  } else {
    try {
      const response = await fetchData("api/cart", userToken);
      dispatch({ type: "SET_CART_ITEMS", payload: response });
    } catch (error) {
      console.error("Error fetching cart items:", error);
    }
  }
}

const syncWithServer = async (cartItems, userToken) => {
  try {
    // Delete all cart items first
    await deleteCartItems(userToken);

    // After deletion, add new cart items
    await postData("/api/cart/add", cartItems, userToken);
  } catch (error) {
    console.error("Error syncing cart items with server:", error);
  }
};

const deleteCartItems = async (userToken) => {
  try {
    await fetch("/api/cart/delete", {
      method: "DELETE",
      headers: {
        Authorization: `Bearer ${userToken}`,
      },
    });
  } catch (error) {
    console.error("Error deleting cart items:", error);
    throw error; // Rethrow the error to be caught by syncWithServer
  }
};

const syncWithLocalStorage = (cartItems) => {
  try {
    localStorage.setItem("cartItems", JSON.stringify(cartItems));
  } catch (error) {
    console.error("Error syncing cart items with local storage:", error);
  }
};
