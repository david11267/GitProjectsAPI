import { useUserApiKey } from "@/hooks/useUserApiKey";

export default function ApiKey() {
  const { status, data } = useUserApiKey();

  if (!data) return <div>{status}</div>;
  return <div>Api key: {data}</div>;
}
