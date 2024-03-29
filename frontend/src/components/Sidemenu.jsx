import React from "react";
import { Link } from "react-router-dom";

function Sidemenu({ isSideMenu, toggleSideMenu }) {
  return (
    <div>
      {isSideMenu && (
        <div className="sidebar">
          <div className="sidebar-content">
            <li>
              <Link onClick={toggleSideMenu} to={"/"}>
                Home
              </Link>
            </li>
            <li>
              <Link onClick={toggleSideMenu} to={"/about"}>
                About
              </Link>
            </li>
            <li>
              <Link onClick={toggleSideMenu} to={"/contact"}>
                Contact
              </Link>
            </li>
          </div>
        </div>
      )}
    </div>
  );
}

export default Sidemenu;
