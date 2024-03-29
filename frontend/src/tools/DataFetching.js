import { useState, useEffect } from "react";

import { getToken } from "./SignIn";
import { baseUrl } from "./backendConfig";
import axios from "axios";
export function useDataFetching(url, token) {
  const [data, setData] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    async function fetchData() {
      try {
        const response = await axios.get(`${baseUrl}/${url}`, {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        });
        let reverseItems;
        Array.isArray(response)
          ? (reverseItems = response.reverse())
          : (reverseItems = response);
        // console.log(reverseItems);
        setData(reverseItems);
        setLoading(false);
        setError(null);
      } catch (error) {
        setError(error);
        // setLoading(false);
      }
    }
    fetchData();
  }, [url]);

  return { data, loading, error };
}

export async function fetchData(url, token) {
  try {
    const response = await axios.get(`${baseUrl}/${url}`, {
      headers: {
        Authorization: `Bearer${token}`,
      },
    });
    let reverseItems;
    Array.isArray(response.data)
      ? (reverseItems = response.data.reverse())
      : (reverseItems = response.data);
    return reverseItems;
  } catch (error) {
    throw error;
  }
}
