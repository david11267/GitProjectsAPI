import { useAuth } from "@clerk/clerk-react";
import { useQuery } from "@tanstack/react-query";

export function useUserApiKey() {
  const { getToken } = useAuth();
  const baseUrl: string = import.meta.env.VITE_API_BASE_URL;

  return useQuery({
    queryKey: ["apiKey"],
    queryFn: async (): Promise<string> => {
      const token = await getToken({ template: "GitProjectsAPIBackend" });

      const response = await fetch(`${baseUrl}/api/key`, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });

      if (!response.ok) {
        throw new Error(`Request failed: ${response.status}`);
      }

      return response.json(); // or response.json(), depending on your backend
    },
  });
}
