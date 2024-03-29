import React, { useEffect, useState } from "react";
import "../Styling/css/components/blogPost.css";
import "../Styling/css/components/card.css";
import "../Styling/css/components/btn.css";
import "../Styling/css/components/loader.css";
import data from "../tools/data.json";
import ProductPageMobile from "./ProductPageMobile";
import ProductPage from "./ProductPage";
import { useParams } from "react-router-dom";
import { useAdminDataFetching } from "../tools/AdminDataFetching";
export default function SingleProductPage({ toggleCart }) {
  const url = "api/products";
  // const { data, loading, error } = useAdminDataFetching(url);
  const { id } = useParams();
  const [productsData, setProductsData] = useState([]);

  useEffect(() => {
    if (data) {
      const products = data.filter((product) => product.id == id);
      setProductsData(products);
    }
  }, [data, id]);

  const isMobile = window.innerWidth <= 599;

  // Only render the child component if productsData is not empty
  return productsData.length > 0 ? (
    isMobile ? (
      <ProductPageMobile
        productsData={productsData[0]}
        toggleCart={toggleCart}
      />
    ) : (
      <ProductPage productsData={productsData[0]} toggleCart={toggleCart} />
    )
  ) : null;
}
