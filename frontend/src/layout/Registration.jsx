import React, { useState } from "react";
import { Link, Navigate } from "react-router-dom";
import "../Styling/css/components/postSubmitted.css";
import SignUpForm from "./SignUpForm";
import GoogleAuth from "../components/GoogleAuth";

function Registration() {
  return (
    <div className="write-container">
      <div className="post-submitted-card mt-32">
        <SignUpForm />
        <GoogleAuth />
      </div>
    </div>
  );
}

export default Registration;
