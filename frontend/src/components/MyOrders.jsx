import React from "react";
import { useDataFetching } from "../tools/DataFetching";
import { useAuthUser, useAuthHeader } from "react-auth-kit";

function MyOrders() {
  const auth = useAuthUser();
  const autha = useAuthHeader();

  const userToken = autha().slice(6);
  const { data, loading, error } = useDataFetching("orders", userToken);
  //add (!) before the loading
  let ordersData = [];
  function formatDate(dateString) {
    const date = new Date(dateString);

    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, "0"); // Adding 1 to month because January is 0
    const day = String(date.getDate()).padStart(2, "0");

    const formattedDate = `${day}-${month}-${year}`;

    return formattedDate;
  }
  console.log(loading);
  console.log(data);
  if (!loading && data) {
    ordersData = data.data;
    console.log(ordersData);
  }

  return (
    <div>
      {!loading && !ordersData.length && (
        <img
          className="no-orders-img"
          src="https://firebasestorage.googleapis.com/v0/b/blogimgupload-3998a.appspot.com/o/no-order-a-flat-rounded-icon-is-up-for-premium-use-vector.jpg?alt=media&token=2e92e1e7-f3bf-4ff9-9a3d-ae8249c25526"
          alt="no orders photo"
        ></img>
      )}
      {ordersData.length > 0 && (
        <div>
          <div className="my-orders-container">
            {ordersData.map((arrayOfProd, index) => (
              <div className="one-order-container" key={index}>
                <div className="order-date">
                  <h2>{formatDate(arrayOfProd[0].date)}</h2>
                </div>
                {arrayOfProd.map((product, subIndex) => (
                  <div className="order-products" key={subIndex}>
                    <div className="order-details">
                      <img
                        src={product.image}
                        className="orders-images"
                        alt={product.title}
                      />
                      <div className="orders-title">
                        <h2>{product.title}</h2>
                        <h2>{product.price} L.E.</h2>
                      </div>
                      <div className="order-status">{product.orderStatus}</div>
                    </div>
                  </div>
                ))}
              </div>
            ))}
          </div>
        </div>
      )}
    </div>
  );
}

export default MyOrders;
