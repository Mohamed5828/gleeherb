import React, { useState } from "react";
import { useSignIn } from "../tools/SignIn"; // Import the custom hook
import "./../Styling/css/components/login.css";
import { Link } from "react-router-dom";

function LoginPage() {
  const signIn = useSignIn(); // Use the custom hook
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [errorMessage, setErrorMessage] = useState("");
  const clearErrorMessage = () => {
    setErrorMessage("");
  };

  const handleLogin = async () => {
    try {
      await signIn(username, password);
    } catch (error) {
      // Check if the error message exists and set the error message state accordingly
      if (
        error.response &&
        error.response.status === 400 &&
        error.response.data.message == "Bad credentials"
      ) {
        setErrorMessage("Invalid email or password"); // Customize error message based on your backend response
      } else {
        console.error("Login failed:", error.message);
        setErrorMessage("An error occurred. Please try again later.");
      }
    }
  };

  return (
    <div className="login-container mt-40">
      <form>
        <label htmlFor="username">Username:</label>
        <input
          type="text"
          id="username"
          value={username}
          onChange={(e) => {
            setUsername(e.target.value);
            clearErrorMessage();
          }}
        />
        <label htmlFor="password">Password:</label>
        <input
          type="password"
          id="password"
          value={password}
          onChange={(e) => {
            setPassword(e.target.value);
            clearErrorMessage();
          }}
        />
        {errorMessage && <p className="error">{errorMessage}</p>}{" "}
        <button className="login-btn" type="button" onClick={handleLogin}>
          Login
        </button>
      </form>
      {/* Display error message if exists */}
      <Link className="register-btn" to="/registration">
        New User
      </Link>
    </div>
  );
}

export default LoginPage;
