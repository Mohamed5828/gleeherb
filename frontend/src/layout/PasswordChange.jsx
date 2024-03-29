import React, { useState } from "react";
import "./../Styling/css/components/login.css";
import { Link } from "react-router-dom";

function PasswordChange() {
  const [newPassword, setNewPassword] = useState("");
  const [confirmedPassword, setConfirmedPassword] = useState("");
  const [currentPassword, setCurrentPassword] = useState("");

  const handlePasswordChange = () => {
    if (newPassword !== confirmedPassword) {
      alert("Confirmed password does not match the new password");
      return;
    }
    // Handle password change logic here
  };

  return (
    <div className="login-container">
      <form>
        <label htmlFor="current-password">Current Password</label>
        <input
          type="password"
          id="current-password"
          value={currentPassword}
          onChange={(e) => setCurrentPassword(e.target.value)}
        />

        <label htmlFor="new-password">New Password</label>
        <input
          type="password"
          id="new-password"
          value={newPassword}
          onChange={(e) => setNewPassword(e.target.value)}
        />

        <label htmlFor="confirm-password">Confirm New Password</label>
        <input
          type="password"
          id="confirm-password"
          value={confirmedPassword}
          onChange={(e) => setConfirmedPassword(e.target.value)}
        />

        <button
          className="login-btn"
          type="button"
          onClick={handlePasswordChange}
        >
          Change Password
        </button>
      </form>
    </div>
  );
}

export default PasswordChange;
