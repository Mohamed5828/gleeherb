import React from "react";
import { useSignout } from "react-auth-kit";
import { useNavigate } from "react-router-dom";

function SignOut() {
  const signOut = useSignout();
  const navigate = useNavigate;

  logout = () => {
    signOut();
    navigate("/login");
  };
  return (
    <div>
      <button onClick={logout}>Sign Out</button>
    </div>
  );
}

export default SignOut;
