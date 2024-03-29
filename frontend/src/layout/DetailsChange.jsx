import React, { useEffect, useState } from "react";
import "../Styling/css/components/postSubmitted.css";
import { useDataFetching } from "../tools/DataFetching";
import { useAuthUser, useAuthHeader } from "react-auth-kit";
import postData from "../tools/DataPosting";

function DetailsChange() {
  const auth = useAuthUser();
  const autha = useAuthHeader();

  const userToken = autha().slice(6);
  const { data, loading } = useDataFetching("api/user-address", userToken);

  const [formData, setFormData] = useState({
    mobile: "",
    addressLine1: "",
    apartment: "",
    area: "",
    building: "",
    city: "",
    floor: "",
  });

  useEffect(() => {
    if (!loading && data) {
      const userData = data.data;
      setFormData({
        mobile: userData.mobile || "",
        addressLine1: userData.addressLine1 || "",
        apartment: userData.apartment || "",
        area: userData.area || "",
        building: userData.building || "",
        city: userData.city || "",
        floor: userData.floor || "",
      });
    }
  }, [data, loading]);

  let cities = [
    "CAIRO",
    "ALEXANDRIA",
    "GIZA",
    "LUXOR",
    "ASWAN",
    "SHARM_EL_SHEIKH",
    "HURGHADA",
    "PORT",
    "SUEZ",
    "ISMAILIA",
    "MANSOURA",
    "TANTA",
    "ASSIUT",
    "SOHAG",
    "ZAGAZIG",
    "DAMIETTA",
    "MINYA",
    "BENI",
    "QENA",
    "BANHA",
    "KAFR_EL_SHEIKH",
  ];

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormData({
      ...formData,
      [name]: value,
    });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    // Add your form submission logic here
    postData("api/user-address/add", formData, userToken);
  };

  return (
    <div className="post-submitted-card ">
      <form onSubmit={handleSubmit} className="form-container">
        <input
          className="form-input"
          type="tel"
          placeholder="Mobile Number"
          name="mobile"
          value={formData.mobile}
          onChange={handleInputChange}
          required
        />
        <select
          className="form-input"
          name="city"
          value={formData.city}
          onChange={handleInputChange}
          required
        >
          <option value="" disabled>
            Select City
          </option>
          {cities.map((city) => (
            <option key={city} value={city}>
              {city}
            </option>
          ))}
        </select>
        <input
          className="form-input"
          type="text"
          name="area"
          placeholder="Area"
          value={formData.area}
          onChange={handleInputChange}
          required
        />
        <input
          className="form-input"
          type="text"
          name="addressLine1"
          placeholder="Address Line 1"
          value={formData.addressLine1}
          onChange={handleInputChange}
          required
        />
        <input
          className="form-input"
          type="text"
          name="apartment"
          placeholder="Apartment"
          value={formData.apartment}
          onChange={handleInputChange}
        />
        <input
          className="form-input"
          type="text"
          name="building"
          placeholder="Building"
          value={formData.building}
          onChange={handleInputChange}
        />
        <input
          className="form-input"
          type="text"
          name="floor"
          placeholder="Floor"
          value={formData.floor}
          onChange={handleInputChange}
        />

        <button type="submit" className="submit-btn">
          Update
        </button>
      </form>
    </div>
  );
}

export default DetailsChange;
