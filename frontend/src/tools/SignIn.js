// SignIn.js
import axios from "axios";
import { useSignIn as useSignInKit } from "react-auth-kit";
import { useNavigate } from "react-router-dom";
import { baseUrl } from "./backendConfig";

async function getLoginData(username, password) {
  const postData = {
    email: username,
    password: password,
  };

  try {
    const response = await axios.post(
      `${baseUrl}/api/v1/auth/authenticate`,
      postData
    );
    return response.data;
  } catch (error) {
    console.error("Error:");
    throw error;
  }
}
async function getToken(username, password) {
  try {
    const data = await getLoginData(username, password);
    return data.token;
  } catch (error) {
    console.error("Error:", error);
    throw error;
  }
}

const authenticateUser = async (token, username) => {
  return {
    token: token,
    expiresIn: 60,
    tokenType: "Bearer",
    authState: { email: username },
  };
};

const useSignIn = () => {
  const navigate = useNavigate();
  const signInKit = useSignInKit();

  const signInAndRedirect = async (username, password) => {
    try {
      const token = await getToken(username, password);
      const authData = await authenticateUser(token, username);
      signInKit(authData);
      navigate("/");
      return Promise.resolve();
    } catch (error) {
      console.error("Error:", error);
      return Promise.reject(error);
      // Handle the error, e.g., display an error message
    }
  };

  return signInAndRedirect;
};

export { getLoginData, useSignIn, getToken };
