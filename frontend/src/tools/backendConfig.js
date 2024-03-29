const getBaseUrl = () => {
  let URL = "";
  process.env.NODE_ENV === "development"
    ? (URL = "http://localhost:8080")
    : (URL = "https://gleeherb-backend-production.up.railway.app");
  return URL;
};

export const baseUrl = getBaseUrl();
