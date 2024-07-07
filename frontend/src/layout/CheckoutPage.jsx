import React, { useState } from "react";
import { useDataFetching } from "../tools/DataFetching";
import { useAuthHeader } from "react-auth-kit";
import "../Styling/css/components/checkout.css";
import CheckoutCart from "../components/CheckoutCart";
import { cardPayment } from "../tools/Paymob";
import MakeCODRequest from "../tools/CODData";
import { useNavigate } from "react-router-dom";

function CheckoutPage() {
  const autha = useAuthHeader();
  const navigate = useNavigate();
  const isMobile = window.innerWidth <= 599;
  const userToken = autha().slice(6);
  const { data, loading, error } = useDataFetching(
    "api/user-address",
    userToken
  );
  //state == true then paymob !!!
  const [paymentMethod, setPaymentMethod] = useState(true);
  const [disName, setDisName] = useState("");
  const handleChange = (state) => {
    setPaymentMethod(state);
  };
  async function handlePayment() {
    if (paymentMethod) {
      cardPayment(userToken, disName);
    } else {
      MakeCODRequest(disName, userToken);
      navigate("/");
    }
  }

  return (
    <div className={isMobile ? "checkout " : "checkout grid"}>
      <div className="checkout-container">
        {data != null && !loading && (
          <div className="checkout-address">
            <h2>Shipping address</h2>
            <div className="shipping-address-info">
              <h3>{data.data.addressLine1}</h3>
              <h3>{`${data.data.apartment} (apartment)/ ${data.data.floor} (floor)`}</h3>
              <h3>{`${data.data.city} / ${data.data.area}`}</h3>
            </div>
          </div>
        )}
        <div className="checkout-address">
          <h2>Payment method</h2>
          <div className="checkout-payment-info">
            <div
              className="cash ceckout-element cursor-pointer"
              onClick={() => {
                handleChange(false);
              }}
            >
              <div className="ceckout-element-info">
                <label htmlFor="paymentMethod">
                  <input
                    type="radio"
                    id="cod"
                    name="paymentMethod"
                    value={!paymentMethod}
                    checked={!paymentMethod}
                  />
                </label>
                <h2>Cash on delivery (COD)</h2>
              </div>
            </div>

            <div
              className="checkout-visa ceckout-element cursor-pointer"
              onClick={() => {
                handleChange(true);
              }}
            >
              <div className="ceckout-element-info">
                <label htmlFor="paymentMethod">
                  <input
                    type="radio"
                    id="paymob"
                    name="paymentMethod"
                    value={paymentMethod}
                    checked={paymentMethod}
                  />
                </label>
                <h2>Paymob</h2>
              </div>

              <div className={`checkout-paymob ${paymentMethod ? "show" : ""}`}>
                {paymentMethod && (
                  <>
                    <div className="checkout-svg">
                      <svg
                        xmlns="http://www.w3.org/2000/svg"
                        viewBox="-270.8 371 102 52"
                        class="eHdoK"
                      >
                        <path
                          fill="none"
                          stroke="currentColor"
                          stroke-miterlimit="10"
                          stroke-width="2"
                          d="M-182 404v16.8c0 .7-.4 1.2-1 1.2h-75.7c-.7 0-1.2-.6-1.2-1.2v-47.6c0-.7.6-1.2 1.2-1.2h75.7c.7 0 1 .6 1 1.2V395m-78-14h78m-17 18h27m-3.9-4.6 4.5 4.6-4.5 4.6"
                        ></path>
                        <circle
                          cx="-255.5"
                          cy="376.5"
                          r="1.5"
                          fill="currentColor"
                        ></circle>
                        <circle
                          cx="-250.5"
                          cy="376.5"
                          r="1.5"
                          fill="currentColor"
                        ></circle>
                        <circle
                          cx="-245.5"
                          cy="376.5"
                          r="1.5"
                          fill="currentColor"
                        ></circle>
                      </svg>
                    </div>
                    <p>
                      After clicking "Pay now", you will be redirected to Paymob
                      to complete your purchase securely
                    </p>
                  </>
                )}
              </div>
            </div>
          </div>
        </div>
      </div>
      <div className="checkout-allcart">
        <CheckoutCart setDisName={setDisName} />
        <div className="place-order">
          <button className="finish-order-btn" onClick={handlePayment}>
            Place your order
          </button>
        </div>
      </div>
    </div>
  );
}

export default CheckoutPage;
