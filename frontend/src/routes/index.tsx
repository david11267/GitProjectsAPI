import { createFileRoute } from "@tanstack/react-router";
import { SignedIn, SignedOut, SignIn, SignInButton, useAuth, UserButton, useUser } from "@clerk/clerk-react";
import Container from "@/components/Container";

export const Route = createFileRoute("/")({
  component: App,
});

function App() {
  const { isSignedIn, user, isLoaded } = useUser();
  const { getToken } = useAuth(); // get Clerk's token helper

  async function testFetch() {
    try {
      const token = await getToken({ template: "GitProjectsAPIBackend" }); // fetch a valid JWT

      const response = await fetch("http://localhost:8080/api/me", {
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
    return <SignIn />;
  }

  return (
    <div>
      <Container>
        Welcome! {user.firstName}
        <div>
          <SignedIn>
            <UserButton />
          </SignedIn>
          <SignedOut>
            <SignInButton />
          </SignedOut>
        </div>
        <button className="bg-amber-400 cursor-pointer" onClick={() => testFetch()}>
          jwt test /me endpoint
        </button>
      </Container>
    </div>
  );
}
