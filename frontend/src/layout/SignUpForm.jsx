import React, { useState } from "react";
import "../Styling/css/components/postSubmitted.css";
import axios, { AxiosError } from "axios";
import { useSignIn } from "react-auth-kit";
import { useNavigate } from "react-router-dom";
import { baseUrl } from "../tools/backendConfig";

const SignUpForm = () => {
  const navigate = useNavigate();

  const [formData, setFormData] = useState({
    firstName: "",
    lastName: "",
    email: "",
    password: "",
    passwordRe: "",
  });
  const [errorMessage, setErrorMessage] = useState("");

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormData({
      ...formData,
      [name]: value,
    });
  };
  const signIn = useSignIn();
  const handleSubmit = async (e) => {
    e.preventDefault();

    // Check if passwords match
    if (formData.password !== formData.passwordRe) {
      setErrorMessage("Passwords do not match");

      return; // Prevent form submission
    }

    const passwordRegex = /^(?=.*[a-zA-Z])(?=.*\d).{6,}$/;
    if (!passwordRegex.test(formData.password)) {
      setErrorMessage(
        "Password must be at least 6 characters long and contain letters and numbers"
      );
      return; // Prevent form submission
    }
    try {
      const response = await axios.post(
        `${baseUrl}/api/v1/auth/register`,
        formData
      );

      signIn({
        token: response.data.token,
        expiresIn: 3600,
        tokenType: "Bearer",
        authState: {
          email: formData.email,
          isVerified: response.data.isVerified,
          addressId: response.data.address,
        },
      });
      navigate("/address");
      console.log(response.data);
    } catch (error) {
      if (error.response && error.response.status === 400) {
        // If the email is already in use, display an error message
        setErrorMessage(error.response.data.message); // Assuming the error message is sent from backend
      } else if (error.isAxiosError && !error.response) {
        // Handle network error
        setErrorMessage(
          "Network Error: Please check your internet connection and try again."
        );
      } else {
        // Handle other types of errors
        console.error("Registration failed:", error.message);
      }
    }
  };

  return (
    <form onSubmit={handleSubmit} className="form-container">
      <input
        className="form-input"
        type="text"
        name="firstName"
        placeholder="First Name"
        value={formData.firstName}
        onChange={handleInputChange}
        required
      />
      <input
        className="form-input"
        type="text"
        name="lastName"
        placeholder="Last Name"
        value={formData.lastName}
        onChange={handleInputChange}
        required
      />
      <input
        className="form-input"
        type="email"
        name="email"
        placeholder="Email"
        value={formData.email}
        onChange={handleInputChange}
        required
      />
      <input
        className="form-input"
        type="password"
        name="password"
        placeholder="Password"
        value={formData.password}
        onChange={handleInputChange}
        required
      />
      <input
        className={
          formData.password != formData.passwordRe && formData.passwordRe != ""
            ? "form-input password-warning"
            : "form-input"
        }
        type="password"
        name="passwordRe"
        placeholder="Rewirte Password"
        value={formData.passwordRe}
        onChange={handleInputChange}
        required
      />{" "}
      <p className="agreement">
        By creating an account, you agree to Egherb's <i>Conditions of Use</i>{" "}
        and <i>Privacy Notice</i>.
      </p>
      {errorMessage && <p className="error">{errorMessage}</p>}
      <button type="submit" className="submit-btn" onClick={handleSubmit}>
        Sign Up
      </button>
    </form>
  );
};

export default SignUpForm;
