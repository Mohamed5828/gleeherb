// React Component with class names
import React, { useState } from "react";
import "./../Styling/css/components/inquiry.css";
function InquiryForm() {
  const [formData, setFormData] = useState({
    name: "",
    email: "",
    phoneNumber: "",
    issue: "",
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prevData) => ({
      ...prevData,
      [name]: value,
    }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    console.log("Form Data:", formData);
    setFormData({
      name: "",
      email: "",
      phoneNumber: "",
      issue: "",
    });
  };

  return (
    <div className="inquiry-container">
      <h1>Support Inquiry</h1>
      <form onSubmit={handleSubmit}>
        <label>
          Name:
          <input
            type="text"
            name="name"
            value={formData.name}
            onChange={handleChange}
            className="input-field"
          />
        </label>

        <label>
          Email:
          <input
            type="email"
            name="email"
            value={formData.email}
            onChange={handleChange}
            className="input-field"
          />
        </label>

        <label>
          Phone:
          <input
            type="text"
            name="phoneNumber"
            value={formData.phoneNumber}
            onChange={handleChange}
            className="input-field"
          />
        </label>

        <label>
          Issue:
          <textarea
            name="issue"
            value={formData.issue}
            onChange={handleChange}
            className="textarea-field"
          />
        </label>

        <button type="submit" className="submit-inquiry">
          Submit
        </button>
      </form>
    </div>
  );
}

export default InquiryForm;
