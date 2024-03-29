import axios from "axios";
import { baseUrl } from "./backendConfig";

const setAuthToken = (token) => {
  if (token) {
    axios.defaults.headers.common["Authorization"] = `Bearer ${token}`;
  } else {
    delete axios.defaults.headers.common["Authorization"];
  }
};
export const handleIncrement = (dispatch, token, productId) => {
  // Increment quantity on the server
  if (!token) {
    // Perform local operation
    changeLocalQuantity(productId, 1);
    dispatch({
      type: "UPDATE_QUANTITY",
      payload: {
        productId,
        quantity: 1, // Assuming the server increments by 1
      },
    });
  } else {
    setAuthToken(token);
    dispatch({
      type: "UPDATE_QUANTITY",
      payload: {
        productId,
        quantity: 1, // Assuming the server increments by 1
      },
    });
    axios
      .put(`${baseUrl}/api/cart/item/${productId}`, 1, {
        headers: {
          "Content-Type": "application/json",
        },
      })
      .then(() => {})
      .catch((error) => {
        console.error("Error:", error);
      });
  }
};

export const handleDecrement = (dispatch, productId, token, quantity) => {
  if (!token) {
    // Perform local operation
    if (quantity > 1) {
      changeLocalQuantity(productId, -1);
      dispatch({
        type: "UPDATE_QUANTITY",
        payload: {
          productId,
          quantity: -1, // Assuming the server increments by 1
        },
      });
    } else {
      handleRemoveLocal(dispatch, productId);
    }
    return;
  }

  setAuthToken(token);

  if (quantity > 1) {
    // Decrement quantity on the server
    dispatch({
      type: "UPDATE_QUANTITY",
      payload: {
        productId,
        quantity: -1, // Assuming the server decrements by 1
      },
    });
    axios
      .put(`${baseUrl}/api/cart/item/${productId}`, -1, {
        headers: {
          "Content-Type": "application/json",
        },
      })
      .then(() => {})
      .catch((error) => {
        console.error("Error:", error);
      });
  } else {
    handleRemove(dispatch, token, productId);
  }
};

export const handleRemove = (dispatch, token, productId) => {
  // Remove item from the cart on the server
  if (!token) {
    handleRemoveLocal(dispatch, productId);
    dispatch({
      type: "REMOVE_FROM_CART",
      payload: {
        productId,
      },
    });
    return;
  }

  setAuthToken(token);
  dispatch({
    type: "REMOVE_FROM_CART",
    payload: {
      productId,
    },
  });
  axios
    .delete(`${baseUrl}/api/cart/delete`, {
      params: { productId: productId },
    })
    .then(() => {})
    .catch((error) => {
      console.error("Error:", error);
    });
};

export const handleAddToCart = (dispatch, token, isAuth, product) => {
  if (isAuth) {
    // User is authenticated, add item to the cart on the server
    setAuthToken(token);
    const cartItem = {
      product: product.id,
      quantity: 1, // Assuming adding an item adds 1 quantity
    };
    dispatch({
      type: "ADD_TO_CART",
      payload: {
        firstImage: product.firstImage,
        title: product.title,
        priceEg: parseInt(product.priceEg),
        id: product.id,
        weight: product.weight,
        quantity: 1,
      },
    });
    axios
      .post(`${baseUrl}/api/cart/add`, cartItem)
      .then(() => {})
      .catch((error) => {
        console.error("Error:", error);
      });
  } else {
    // User is not authenticated, save item to local storage
    let cartItems = JSON.parse(localStorage.getItem("cartItems")) || [];
    const newItem = {
      firstImage: product.firstImage,
      title: product.title,
      priceEg: parseInt(product.priceEg),
      id: product.id,
      weight: product.weight,
      quantity: 1,
    };
    cartItems.push(newItem);
    localStorage.setItem("cartItems", JSON.stringify(cartItems));

    // Dispatch an action if needed, e.g., to update UI state
    dispatch({
      type: "ADD_TO_CART",
      payload: newItem,
    });
  }
};
const changeLocalQuantity = (productId, quantity) => {
  const cartItems = JSON.parse(localStorage.getItem("cartItems")) || [];
  const updatedCartItems = cartItems.map((item) => {
    if (item.id === productId) {
      return { ...item, quantity: item.quantity + quantity };
    }
    return item;
  });
  localStorage.setItem("cartItems", JSON.stringify(updatedCartItems));
};
export const handleRemoveLocal = (dispatch, productId) => {
  let cartItems = JSON.parse(localStorage.getItem("cartItems")) || [];
  cartItems = cartItems.filter((item) => item.id !== productId);
  localStorage.setItem("cartItems", JSON.stringify(cartItems));
  dispatch({
    type: "REMOVE_FROM_CART",
    payload: {
      productId,
    },
  });
};
