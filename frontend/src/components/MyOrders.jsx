import React from "react";
import { useDataFetching } from "../tools/DataFetching";
import { useAuthUser, useAuthHeader } from "react-auth-kit";

function MyOrders() {
  const auth = useAuthUser();
  const autha = useAuthHeader();

  const userToken = autha().slice(6);
  // const { data, loading, error } = useDataFetching("orders", userToken);
  //add (!) before the loading

  // let ordersData = {};
  let ordersData = [
    {
      title: "ARGANILLA ARGAN OIL HAIR SERUM",
      previewImage:
        "https://firebasestorage.googleapis.com/v0/b/blogimgupload-3998a.appspot.com/o/Glee%2Fserum%20(1).png?alt=media&token=01ae2ea5-14ef-4e40-ab28-de4b9d955247",
      firstImage:
        "https://firebasestorage.googleapis.com/v0/b/blogimgupload-3998a.appspot.com/o/Glee%2F1%20(1).png?alt=media&token=c9cb66af-4c39-4214-bc39-632e8512719f",
      secondImage:
        "https://firebasestorage.googleapis.com/v0/b/blogimgupload-3998a.appspot.com/o/Glee%2F2%20(1).png?alt=media&token=08e3c8ab-6e97-4c1b-93d3-bd5e3a00ad5c",
      id: 3,
      weight: 160,
      quantity: 50,
      priceEg: 550,
      firstAvailable: "December 2024",
      expiryDate: "December 2027",
      productBatch: "AS001",
      description:
        "<p>Promotes Hair Growthin Cases of Damaged Hair Moisturize and Soften Hair Strengthen Hair and Nourish Scalp. <ul><li>No SULFATES</li>NO PARABIENS<li>NO FORMALDEHYDE</li><li>NO DEA</li><li>NO ARTIFICIAL DYES</li><li>NO GLUTEN</li><li>NO PABA</li><li>NO MINERAL OIL</li><li>NO PETROLATUM</li><li>NO PARAFFIN</li><li>NO ANIMAL TESTING</li></ul></p>",

      suggestedUse:
        "On Clean and Damp Hair, Apply a Small Quantity on Hair of Scalp, Then Rub Gently for 3-5 Minutes, and Repeat the Use as When Needed.",

      otherIngredients:
        "<p>Cyclopentasiloxane, Dimethiconol, Dimethicone Argania Spinosa Kernel Oil, Linum Usitatissimum Seed Oil, Parfum, BHT, Caprylic/Capric Triglyceride, Tocopherol, Simmondsia Chinensis Seed Oil, CI 26100, CI 47000.</p>",
      warning:
        "<ul><li>Avoid Contact With Eyes</li><li>Store at a Temperature Not Exceeding 30 C, In a Dry Place. </li><li>Keep Out Of Reach Of Children.</li><li>For External Use Only.</li></ul>",
    },
    {
      previewImage:
        "https://firebasestorage.googleapis.com/v0/b/blogimgupload-3998a.appspot.com/o/Glee%2Fbundles-logo%2F1.png?alt=media&token=a7bb144b-3373-4e47-a0cc-cc999b727fd2",
      firstImage:
        "https://firebasestorage.googleapis.com/v0/b/blogimgupload-3998a.appspot.com/o/Glee%2Fbundles-logo%2F1.png?alt=media&token=a7bb144b-3373-4e47-a0cc-cc999b727fd2",
      id: 100,
      title: "Shampoo & Conditioner",
      priceEg: 950,
      weight: "(350 + 350)",
    },
  ];
  // console.log(loading);
  // console.log(data);
  // if (!loading && data) {
  //   ordersData = data.data;
  //   console.log(ordersData);
  // }

  return (
    <div>
      {/* {!loading && !ordersData.length && ( */}
      {!ordersData.length && (
        <img
          className="no-orders-img"
          src="https://firebasestorage.googleapis.com/v0/b/blogimgupload-3998a.appspot.com/o/no-order-a-flat-rounded-icon-is-up-for-premium-use-vector.jpg?alt=media&token=2e92e1e7-f3bf-4ff9-9a3d-ae8249c25526"
          alt="no orders photo"
        ></img>
      )}
      {ordersData.length > 0 && (
        <div>
          <div className="my-orders-container">
            {ordersData.map((product, index) => (
              <div className="one-order-container">
                <div className="order-date">
                  <h2>21/5/2024</h2>
                </div>

                <div key={index} className="order-details">
                  <img src={product.firstImage} className="orders-images" />
                  <div className="orders-title">
                    <h2>{product.title}</h2>
                    <h2>{product.priceEg} L.E.</h2>
                  </div>
                  <div className="order-status">Delivered</div>
                </div>
              </div>
            ))}
          </div>
        </div>
      )}
    </div>
  );
}

export default MyOrders;
