import React, { useState } from "react";
import DetailsChange from "./DetailsChange";
import PasswordChange from "./PasswordChange";
import ProfileSidebar from "../components/ProfileSidebar";
import MyOrders from "../components/MyOrders";

function ProfileMobile() {
  const [activeTab, setActiveTab] = useState("myOrder");
  return (
    <div>
      <ProfileSidebar
        isMobile={true}
        activeTab={activeTab}
        setActiveTab={setActiveTab}
      />
      {activeTab === "myOrder" && (
        <div className="my-orders-section">
          <MyOrders />
        </div>
      )}
      {activeTab === "accountDetails" ? (
        <div className="account-details-section">
          <DetailsChange />
        </div>
      ) : (
        ""
      )}
      {activeTab === "accountPassword" ? (
        <div className="change-password">
          <PasswordChange />
        </div>
      ) : (
        ""
      )}
    </div>
  );
}
export default ProfileMobile;
