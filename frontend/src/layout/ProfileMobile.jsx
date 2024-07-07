import React, { useState } from "react";
import DetailsChange from "./DetailsChange";
import PasswordChange from "./PasswordChange";
import ProfileSidebar from "../components/ProfileSidebar";
import MyOrders from "../components/MyOrders";

function ProfileMobile() {
  const [activeTab, setActiveTab] = useState("myOrder");
  return (
    <div className="profile-main-container">
      <ProfileSidebar
        isMobile={true}
        activeTab={activeTab}
        setActiveTab={setActiveTab}
      />
      {activeTab === "myOrder" && (
        <div className="my-orders-section zero-left">
          <MyOrders />
        </div>
      )}
      {activeTab === "accountDetails" ? (
        <div className="account-details-section zero-left">
          <DetailsChange />
        </div>
      ) : (
        ""
      )}
      {activeTab === "accountPassword" ? (
        <div className="change-password zero-left">
          <PasswordChange />
        </div>
      ) : (
        ""
      )}
    </div>
  );
}
export default ProfileMobile;
