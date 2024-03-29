import React, { useState } from "react";
import Navbar from "./components/Navbar";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import { AuthProvider, RequireAuth } from "react-auth-kit";
import SingleProductPage from "./layout/SingleProductPage";
import Cart from "./components/Cart";
import Registration from "./layout/Registration";
import LoginPage from "./components/LoginPage";
import { CartProvider } from "./tools/CartContext";
import NewHome from "./layout/NewHome";
import InquiryForm from "./layout/Inquiry";
import Sidemenu from "./components/Sidemenu";
import RegInfo from "./components/RegInfo";
import Profile from "./layout/ProfileNormal";
import ScrollToTop from "./tools/ScrollToTop";
import About from "./layout/About";
import Contact from "./layout/Contact";

function App() {
  const [isCartOpen, setIsCartOpen] = useState(false);

  function toggleCart() {
    return setIsCartOpen(!isCartOpen);
  }

  const [isSideMenu, setSideMenu] = useState(false);
  function toggleSideMenu() {
    setSideMenu((prevState) => (prevState = !prevState));
  }

  return (
    <AuthProvider
      authType={"cookie"}
      authName={"_auth"}
      cookieDomain={window.location.hostname}
      cookieSecure={window.location.protocol === "https:"}
    >
      <CartProvider>
        <Router>
          <ScrollToTop />
          <Navbar
            isCartOpen={isCartOpen}
            toggleCart={toggleCart}
            isSideMenu={isSideMenu}
            toggleSideMenu={toggleSideMenu}
          />
          <Sidemenu isSideMenu={isSideMenu} toggleSideMenu={toggleSideMenu} />
          <Cart isCartOpen={isCartOpen} toggleCart={toggleCart} />
          <Routes>
            <Route path="/" element={<NewHome toggleCart={toggleCart} />} />
            <Route
              path="/product/:id"
              element={<SingleProductPage toggleCart={toggleCart} />}
            />
            <Route path="/registration" element={<Registration />} />
            <Route path="/address" element={<RegInfo />} />
            <Route path="/contact" element={<Contact />} />
            <Route path="/login" element={<LoginPage />} />
            <Route path="/profile" element={<Profile />} />
            <Route path="/inquiry" element={<InquiryForm />} />
            <Route path="/about" element={<About />} />
            <Route
              path="/payment"
              element={<RequireAuth loginPath="/login"></RequireAuth>}
            />
          </Routes>
        </Router>
      </CartProvider>
    </AuthProvider>
  );
}

export default App;
