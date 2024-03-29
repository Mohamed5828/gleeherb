import { baseUrl } from "./backendConfig";
import axios from "axios";

const postData = async (url, data, token) => {
  try {
    const response = await axios.post(`${baseUrl}/${url}`, data, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });
    return response;
  } catch (error) {
    console.error(error);
  }
};

export default postData;
