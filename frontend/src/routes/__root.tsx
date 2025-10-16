import { Outlet, createRootRoute } from "@tanstack/react-router";
import { TanStackRouterDevtoolsPanel } from "@tanstack/react-router-devtools";
import { TanStackDevtools } from "@tanstack/react-devtools";
import ClerkProvider from "../integrations/clerk/provider";
import Background from "@/components/Background";

export const Route = createRootRoute({
  component: () => (
    <>
      <ClerkProvider>
        <Background />
        <Outlet />
        <TanStackDevtools
          config={{
            position: "bottom-right",
          }}
          plugins={[
            {
              name: "Tanstack Router",
              render: <TanStackRouterDevtoolsPanel />,
            },
          ]}
        />
      </ClerkProvider>
    </>
  ),
});
