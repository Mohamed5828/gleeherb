import React from "react";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faFacebookF } from "@fortawesome/free-brands-svg-icons";
import { faInstagram } from "@fortawesome/free-brands-svg-icons";
import "../Styling/css/components/footer.css";
import { Link } from "react-router-dom";
function Footer() {
  return (
    <div className="footer">
      <div className="footer-info">
        <h1 className="font-bold">Contact</h1>
        <ul className="footer-contact">
          <li>address: 2643 District 2 Area 8 - 6th October City - Giza</li>
          <li>Phone: 01050888650 - 01050888640</li>
          <li>Working Hours: 9 AM - 5 PM</li>
          <li>Our Social Networks</li>
        </ul>
        <ul className="footer-social">
          <li>
            {" "}
            <Link
              to={"https://facebook.com/GleeherbEg"}
              className="navbar-icons"
            >
              <FontAwesomeIcon icon={faFacebookF} />
            </Link>
          </li>
          <li>
            {" "}
            <Link
              to={"https://instagram.com/gleeherbegypt/"}
              className="navbar-icons"
            >
              <FontAwesomeIcon icon={faInstagram} />
            </Link>
          </li>
        </ul>
      </div>
      <div className="footer-img-div">
        <img
          className="footer-logo"
          src="https://firebasestorage.googleapis.com/v0/b/blogimgupload-3998a.appspot.com/o/Glee%2Fbundles-logo%2FGlee%20herb.png?alt=media&token=b80ea828-501b-40b2-a59f-5d5d2a85c62b"
          alt="logo"
        />
      </div>
    </div>
  );
}

export default Footer;
