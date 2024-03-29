import React from "react";
import ProfileMobile from "./ProfileMobile";
import ProfileNormal from "./ProfileNormal";

function Profile() {
  const isMobile = window.innerWidth <= 599;

  return isMobile ? <ProfileMobile /> : <ProfileNormal />;
}

export default Profile;
