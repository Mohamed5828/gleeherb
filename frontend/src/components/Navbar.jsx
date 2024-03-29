import React, { useState, useEffect } from "react";
import "../Styling/css/components/navbar.css";
import { Link, useLocation } from "react-router-dom";
import SigninBtn from "./SigninBtn";
import { DevicePhoneMobileIcon } from "@heroicons/react/24/outline";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faFacebookF } from "@fortawesome/free-brands-svg-icons";
import { faInstagram } from "@fortawesome/free-brands-svg-icons";
import { faBars } from "@fortawesome/free-solid-svg-icons";
import { faShoppingCart } from "@fortawesome/free-solid-svg-icons";
import { faUser } from "@fortawesome/free-solid-svg-icons";
function Navbar({ isCartOpen, toggleCart, isSideMenu, toggleSideMenu }) {
  const [isSticky, setSticky] = useState(false);
  const location = useLocation();
  const isMobile = window.innerWidth <= 599;

  useEffect(() => {
    const handleScroll = () => {
      const offset = window.scrollY;
      if (offset > 30) {
        setSticky(true);
      } else {
        setSticky(false);
      }
    };

    window.addEventListener("scroll", handleScroll);

    return () => {
      window.removeEventListener("scroll", handleScroll);
    };
  }, []);
  return (
    <>
      <header className={`navbar `}>
        <div className="navbar-ribbon ">
          <div className="working-time">
            <p>Opens Daily 9 AM - 5 PM</p>
          </div>
          <div className="navbar-contact">
            <div className="navbar-phone inline-block">
              <DevicePhoneMobileIcon className="h-4 w-4 inline-block" />
              <p className="inline-block">(+2) 01050888650 |</p>
            </div>
            <div className="navbar-social">
              <Link
                to={"https://facebook.com/GleeherbEg"}
                className="navbar-icons"
              >
                <FontAwesomeIcon icon={faFacebookF} />
              </Link>
              <Link
                to={"https://instagram.com/gleeherbegypt/"}
                className="navbar-icons"
              >
                <FontAwesomeIcon icon={faInstagram} />
              </Link>
            </div>
          </div>
        </div>
        <div className={`${!isMobile ? " " : "navbar-mobile"}`}>
          <div className={!isMobile ? "main-nav-container " : ""}>
            <div className={!isMobile ? "left-navbar" : ""}>
              <ul className="navbar-links">
                <li>
                  <Link to={"/"}>Home</Link>
                </li>
                <li>
                  <Link to={"/about"}>About</Link>
                </li>
              </ul>
            </div>
            <div className={!isMobile ? "mid-navbar" : ` mid-mobile`}>
              <Link to={"/"}>
                <img
                  className="navbar-logo"
                  src="https://firebasestorage.googleapis.com/v0/b/blogimgupload-3998a.appspot.com/o/Glee%2Fbundles-logo%2FGlee%20herb.png?alt=media&token=b80ea828-501b-40b2-a59f-5d5d2a85c62b"
                />
              </Link>
            </div>
            <div className={!isMobile ? "right-navbar" : ""}>
              <ul className="navbar-links">
                <li>
                  <Link to={"/"}>Blog</Link>
                </li>
                <li>
                  <Link to={"/contact"}>Contact</Link>
                </li>
              </ul>
              <ul className="navbar-links">
                <li>
                  <div className="cart-div" onClick={toggleCart}>
                    <FontAwesomeIcon
                      icon={faShoppingCart}
                      className="text-2xl	"
                    />
                    <div>
                      <span>Cart</span>
                    </div>
                  </div>
                </li>
                <li>
                  <div className="sign-in-div">
                    <SigninBtn />
                  </div>
                </li>
              </ul>
            </div>
          </div>
          {isMobile && (
            <div className="mobile-nav-icons">
              <div className="navbar-menu" onClick={toggleSideMenu}>
                <FontAwesomeIcon icon={faBars} className="bars" />
              </div>
              <ul className="navbar-links-mobile">
                <li>
                  <div className="cart-div" onClick={toggleCart}>
                    <FontAwesomeIcon
                      icon={faShoppingCart}
                      className="text-lg"
                    />
                  </div>
                </li>
                <li>
                  <div className="sign-in-div">
                    <SigninBtn isMobile={isMobile} />
                  </div>
                </li>
              </ul>
            </div>
          )}
        </div>
      </header>
    </>
  );
}

export default Navbar;
