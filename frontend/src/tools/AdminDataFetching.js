import { useState, useEffect } from "react";
import { USERNAME, PASSWORD } from "./config";
import { getToken } from "./SignIn";
import { baseUrl } from "./backendConfig";
import axios from "axios";

export function useAdminDataFetching(url) {
  const [data, setData] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    async function fetchAdminData() {
      try {
        const token = await getToken(USERNAME, PASSWORD);
        const response = await axios.get(`${baseUrl}/${url}`, {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        });
        let reverseItems;
        Array.isArray(response)
          ? (reverseItems = response.reverse())
          : (reverseItems = response);
        console.log(reverseItems);
        setData(reverseItems);
        setLoading(false);
        setError(null);
      } catch (error) {
        setError(error);
        console.error(error);
      }
    }

    fetchAdminData();
  }, [url]);

  return { data, loading, error };
}
