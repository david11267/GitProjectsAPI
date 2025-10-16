import { createFileRoute } from "@tanstack/react-router";
import { SignedIn, SignedOut, SignIn, SignInButton, useAuth, UserButton, useUser } from "@clerk/clerk-react";
import Container from "@/components/BentoComponents/Container";
import ApiKey from "@/components/BentoComponents/ApiKey";

export const Route = createFileRoute("/")({
  component: App,
});

function App() {
  const { isSignedIn, user, isLoaded } = useUser();
  const { getToken } = useAuth(); // get Clerk's token helper

  async function testFetch() {
    try {
      const token = await getToken({ template: "GitProjectsAPIBackend" }); // fetch a valid JWT

      const response = await fetch("http://localhost:8080/api/key", {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });

      if (!response.ok) {
        throw new Error(`Request failed: ${response.status}`);
      }

      const data = await response.text(); // or response.json() depending on backend
      console.log("✅ Backend response:", data);
    } catch (error) {
      console.error("❌ Error calling backend:", error);
    }
  }

  if (!isSignedIn) {
    return (
      <div className="h-screen w-screen flex items-center justify-center p-8  ">
        <SignIn />
      </div>
    );
  }

  return (
    <div className="h-screen w-screen flex items-center justify-center p-8  ">
      <div className="grid grid-cols-4 gap-4 text-white">
        <div className="col-span-4">
          <UserButton />
        </div>
        <Container>
          <button onClick={() => testFetch()} className="cursor-pointer">
            Test /api/key
          </button>
        </Container>
        <Container className="col-span-3">
          <ApiKey />
        </Container>
        <Container>
          <p>Remaining api calls</p>
          <p>Refresh quota dateTime</p>
        </Container>
        <Container className="col-span-2">Api key usage timeline graph</Container>
        <Container className="col-span-2">Api key options</Container>
      </div>
    </div>
  );
}
