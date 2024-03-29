import axios from "axios";
import React, { useEffect } from "react";
import { useSignIn as useSignInKit } from "react-auth-kit";
import { useNavigate } from "react-router-dom";
import { baseUrl } from "../tools/backendConfig";

function GoogleAuth() {
  const signInKit = useSignInKit();
  const navigate = useNavigate();

  function handleCallbackResponse(response) {
    console.log("Encoded jwt ID token : " + response.credential);
    exchangeGoogleJwtWithToken(response.credential);
  }
  async function exchangeGoogleJwtWithToken(googleToken) {
    try {
      const response = await axios.post(`${baseUrl}/api/auth/google`, {
        token: `${googleToken}`,
      });
      const customToken = response.data.token;
      signInWithCustomToken(customToken);
    } catch (error) {
      console.log(error);
    }
  }
  async function signInWithCustomToken(loginToken) {
    try {
      signInKit({
        token: loginToken,
        expiresIn: 3600,
        tokenType: "Bearer",
      });
      navigate("/address");
    } catch (error) {
      console.error("Error signing in with custom token:", error);
    }
  }

  useEffect(() => {
    /* global google */
    google.accounts.id.initialize({
      client_id:
        "736728232259-93dntg1imd18ai6l9fvh0f006cfs394c.apps.googleusercontent.com",
      callback: handleCallbackResponse,
    });
    google.accounts.id.renderButton(document.getElementById("signInDiv"), {
      theme: "outline",
      size: "large",
    });
    google.accounts.id.prompt();
  }, []);
  return (
    <div>
      <div id="signInDiv"></div>
    </div>
  );
}

export default GoogleAuth;
