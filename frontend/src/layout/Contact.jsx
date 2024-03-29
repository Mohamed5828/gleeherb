import React from "react";
import MapContainer from "../components/MapComponent";
import Footer from "../components/Footer";

function Contact() {
  const latitude = 29.957785;
  const longitude = 30.946835;
  return (
    <div>
      <div className="map-container">
        <MapContainer latitude={latitude} longitude={longitude} />
      </div>
      <div className="contact-details">
        <ul className="footer-contact">
          <h1 className="contact-title">Contact Details</h1>
          <h2 className="office">Office Info</h2>
          <li className="contact-li">
            Address: 2643 District 2 Area 8 - 6th October City - Giza
          </li>
          <li className="contact-li">Phone: 01050888650 - 01050888640</li>
          <li className="contact-li">Email: info@gleeherb.com</li>
        </ul>
      </div>
      <Footer />
    </div>
  );
}

export default Contact;
