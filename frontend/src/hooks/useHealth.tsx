import { useAuth } from "@clerk/clerk-react";
import { useQuery } from "@tanstack/react-query";

const { getToken } = useAuth(); // get Clerk's token helper
const baseUrl: string = import.meta.env.VITE_API_BASE_URL;
const token = await getToken({ template: "GitProjectsAPIBackend" }); // fetch a valid JWT

export function useHealth() {
  return useQuery({
    queryKey: ["health"],
    queryFn: async (): Promise<string> => {
      const response = await fetch(`${baseUrl}/api/health`, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      return await response.json();
    },
  });
}
