import React, { useState, useEffect } from "react";
import { useAuthHeader } from "react-auth-kit";
import postData from "../tools/DataPosting";
import { useDataFetching } from "../tools/DataFetching";

function CheckoutCart({ setDisName }) {
  const [discountCode, setDiscountCode] = useState("");
  const [discountValue, setDiscountValue] = useState("");
  const [discountMessage, setDiscountMessage] = useState("");

  const authHeader = useAuthHeader();
  const userToken = authHeader().slice(6);

  const { data, loading, error } = useDataFetching("api/cart", userToken);

  const handleChange = (e) => {
    setDiscountCode(e.target.value.toUpperCase());
    setDiscountMessage("");
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      let disObj = { discountName: discountCode };
      const response = await postData("api/discount", disObj, userToken);
      console.log(response);
      if (response.data == 1) {
        setDiscountMessage("Invalid discount code");
      } else {
        setDiscountMessage("Discount Applied");
      }
      setDisName(discountCode);
      setDiscountValue(response.data);
    } catch (error) {
      handleDiscountError(error);
    }
  };

  const handleDiscountError = (error) => {
    if (error.response && error.response.status === 400) {
      setDiscountMessage(error.response.data.message);
    } else {
      setDiscountMessage("Failed to apply discount. Please try again later.");
      console.error("Discount failed:", error.message);
    }
  };

  const calculateTotal = () => {
    let total = 0;
    if (!loading && data !== null) {
      total = data.data.reduce((accumulator, currentValue) => {
        return (
          accumulator + currentValue.priceEg * parseInt(currentValue.quantity)
        );
      }, 0);
    }
    return total;
  };

  const calculateDiscountedTotal = () => {
    const total = calculateTotal();
    return discountValue ? total - total * parseFloat(discountValue) : total;
  };

  return (
    <div className="checkout-cart-components">
      <div className="checkout-cart">
        {data !== null &&
          !loading &&
          data.data.map((product) => (
            <div className="checkout-product-container" key={product.id}>
              <div className="checkout-product-img">
                <div className="checkout-product-quantity">
                  <p>{product.quantity}</p>
                </div>
                <img src={product.firstImage} alt="product image" />
              </div>
              <div className="checkout-product-title">
                <h2>{product.title}</h2>
              </div>
              <div className="checkout-product-price">
                <h2>{product.priceEg} L.E.</h2>
              </div>
            </div>
          ))}
      </div>
      <div className="checkout-summary">
        <form onSubmit={handleSubmit} className="checkout-form">
          <input
            type="text"
            value={discountCode}
            placeholder="Discount Code"
            onChange={handleChange}
            className="checkout-discount-input"
          />
          <button type="submit" className="checkout-discount-btn">
            Apply
          </button>
        </form>
        {discountMessage && (
          <p
            className={
              discountMessage === "Discount Applied"
                ? "discount-message green"
                : "discount-message"
            }
          >
            {discountMessage}
          </p>
        )}
        <h1>Order Summary</h1>

        <div className="checkout-order-discount">
          <h2>Subtotal</h2>
          <h3>{calculateTotal()} EGP</h3>
        </div>

        {discountValue !== "" && (
          <div className="checkout-order-discount">
            <h2>Discount value</h2>
            <h3>{calculateDiscountedTotal().toFixed(2)} EGP</h3>
          </div>
        )}

        <div className="checkout-order-discount">
          <h2>Order Total</h2>
          <h3 className="font-bold">
            {(calculateTotal() - calculateDiscountedTotal()).toFixed(2)} EGP
          </h3>
        </div>
      </div>
    </div>
  );
}

export default CheckoutCart;
