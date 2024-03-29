import React, { useState, useEffect } from "react";
import "../Styling/css/components/card.css";
import "../Styling/css/components/loader.css";
import "../Styling/css/components/newHome.css";
import "../Styling/css/components/btn.css";
import "../Styling/css/components/home.css";
import { Link } from "react-router-dom";
import ImageSlider from "../components/ImageSlider";
import Footer from "../components/Footer";
import PlayerComponent from "../components/Video";
import { useCart } from "../tools/CartContext";
import { handleAddToCart } from "../tools/CartHandlers";
import { useAuthHeader, useIsAuthenticated } from "react-auth-kit";
import { useAdminDataFetching } from "../tools/AdminDataFetching";
import data from "../tools/data.json";

const slides = [
  {
    image:
      "https://firebasestorage.googleapis.com/v0/b/blogimgupload-3998a.appspot.com/o/Glee%2F6dc65678-3836-476a-83f1-c88b7f945068.jpg?alt=media&token=563bfedb-be7f-4259-ba8f-7eed99e0f2ba",
    id: 1,
  },
];
const cards = [
  {
    title: "Unleash the Power of Argan Oil for Luxurious Hair Care",
    content: `Glee Herb unlocks the secrets of Morocco's "liquid gold," argan oil. Our exquisite argan oil-infused shampoos, conditioners, and serums elevate your hair care routine to new heights. Experience the transformative power of nature's most precious oil, formulated for ultimate nourishment and shine.`,
  },
  {
    title: "Nourish, Restore, Transform: The Glee Herb Argan Oil Collection",
    content:
      "Dry, damaged, or lackluster hair? Glee Herb's argan oil collection offers a solution for every hair concern. Our shampoos gently cleanse and revitalize, while conditioners deeply nourish and detangle. For an extra touch of luxury, our argan oil serums provide intense hydration, manageability, and a radiant, healthy shine.",
  },
  {
    title: "Confidence & Care: The Glee Herb Argan Oil Collection",
    content:
      "Experience the peace of mind that comes with knowing your hair is in good hands. Glee Herb's argan oil collection is formulated with the highest quality ingredients, ensuring the best possible care plus, our dedicated customer support team is always here to answer your questions and provide assistance.",
  },
];
function NewHome({ toggleCart }) {
  const [hovered, setHovered] = useState(null);
  const { dispatch } = useCart();
  const autha = useAuthHeader();
  const isAuth = useIsAuthenticated();
  const userToken = autha().slice(6);
  const handleMouseEnter = (index) => {
    setHovered(index);
  };
  // const url = "api/products";
  // const { data, loading, error } = useAdminDataFetching(url);
  const [productsData, setProductsData] = useState([]);
  const [bundles, setBundles] = useState([]);
  useEffect(() => {
    if (data) {
      const products = data.filter((product) => product.id < 100);
      const bundles = data.filter((product) => product.id >= 100);
      setProductsData(products);
      setBundles(bundles);
    }
  }, [data]);

  const handleMouseLeave = () => {
    setHovered(null);
  };

  return (
    <div>
      <div className="home-container">
        <div className="home-video">
          <PlayerComponent />
        </div>
        <div className="popular-header-container">
          <h2>Our Products</h2>
        </div>
        <div className="popular-container row gap-3">
          {productsData.map((product, index) => (
            <div className=" col-9-xs col-5-sm col-4-md col-3-xl">
              <Link to={`/product/${product.id}`}>
                <div
                  className={`popular-product-card ${
                    hovered === index ? "product-hovered" : ""
                  }`}
                  key={index}
                  onMouseEnter={() => handleMouseEnter(index)}
                  onMouseLeave={handleMouseLeave}
                >
                  <img
                    src={
                      hovered === index
                        ? product.firstImage
                        : product.previewImage
                    }
                    className="product-img "
                  />
                  <div className="popular-product-info">
                    <h2 className="popular-product-title">{product.title}</h2>
                    <h3 className="popular-product-price">
                      {product.priceEg} L.E.
                    </h3>
                  </div>
                </div>
              </Link>
            </div>
          ))}
        </div>

        <div className="popular-header-container">
          <h2>Bundles</h2>
        </div>
        <div className="popular-container row gap-20">
          {bundles.map((product) => (
            <div
              className="popular-product-card col-9-xs col-5-sm col-5-md col-4-xl cursor-pointer	"
              onClick={() => {
                toggleCart();
                handleAddToCart(dispatch, userToken, isAuth(), product);
              }}
            >
              <div className="popular-product-img">
                <img src={product.previewImage} />
              </div>
              <div className="popular-product-info">
                <h2 className="popular-product-title">
                  {product.title}
                  <br />
                </h2>
                <br />

                <h3 className="popular-product-price">
                  {product.priceEg} L.E.
                </h3>
              </div>
            </div>
          ))}
          <div className="banner">
            <h2 className="banner-header">What is Glee Herb</h2>
            <div className="banner-cards">
              {cards.map((card) => {
                return (
                  <div className="banner-single-card">
                    <div className="banner-card-title">{card.title}</div>
                    <div className="banner-card-content">{card.content}</div>
                  </div>
                );
              })}
            </div>
            <div className="banner-question">
              <h2>Have a question? Well, we’ve got some answers.</h2>
              <Link className="contact-btn" to={"/inquiry"}>
                Contact Us
              </Link>
            </div>
          </div>
        </div>
      </div>
      <div className="home-slider">
        <ImageSlider slides={slides} />
      </div>

      <Footer />
    </div>
  );
}

export default NewHome;
