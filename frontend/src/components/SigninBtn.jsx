import React from "react";
import { useAuthUser, useIsAuthenticated } from "react-auth-kit";
import { useNavigate } from "react-router-dom";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faUser } from "@fortawesome/free-solid-svg-icons";

function SigninBtn({ isMobile }) {
  const isAuthenticated = useIsAuthenticated();
  const auth = useAuthUser();

  const navigate = useNavigate();

  const handleButtonClick = () => {
    // Handle button click based on authentication status
    console.log("isAuthenticated:", isAuthenticated());

    if (isAuthenticated()) {
      // User is authenticated, perform logout or other actions
      navigate("/profile");
    } else {
      // User is not authenticated, perform login or other actions
      navigate("/login");
    }
  };
  return !isMobile ? (
    <div onClick={handleButtonClick}>
      <FontAwesomeIcon icon={faUser} className="text-2xl	sign-in-icon" />
      <div>
        <span>{isAuthenticated() ? "Profile" : "Login"}</span>
      </div>
    </div>
  ) : (
    <div onClick={handleButtonClick}>
      <FontAwesomeIcon icon={faUser} className="text-lg cursor-pointer" />
    </div>
  );
}

export default SigninBtn;
