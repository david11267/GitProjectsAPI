import { createFileRoute } from "@tanstack/react-router";
import { SignIn, UserButton, useUser } from "@clerk/clerk-react";
import Container from "@/components/BentoComponents/Container";
import ApiKey from "@/components/BentoComponents/ApiKey";

export const Route = createFileRoute("/")({
  component: App,
});

function App() {
  const { isSignedIn } = useUser();
  if (!isSignedIn) {
    return (
      <div className="h-screen w-screen flex items-center justify-center p-8  ">
        <SignIn />
      </div>
    );
  }

  return (
    <>
      <div className="h-screen w-screen flex items-center justify-center p-8  ">
        <div className="grid grid-cols-4 gap-4 text-white">
          <div className="col-span-4">
            <UserButton />
          </div>
          <Container className="col-span-3">
            <ApiKey />
          </Container>
          <Container>
            <p>Remaining api calls</p>
            <p>Refresh quota dateTime</p>
          </Container>
          <Container className="col-span-2">Api key usage timeline graph</Container>
          <Container className="col-span-2">Api key options</Container>

          {/*Change the condition of rendering to be proper auth instead of local */}
          {import.meta.env.DEV && (
            <>
              <Container className="col-span-4 mt-16 font-black text-red-500 text-center">
                This is running in dev and below are controlls to be implemented
              </Container>
              <Container className="col-span-4 ">Select user to configure here...</Container>
              <Container>update api key</Container>
              <Container>update update api key quota</Container>
              <Container>update update api key settings</Container>
            </>
          )}
        </div>
      </div>
    </>
  );
}
