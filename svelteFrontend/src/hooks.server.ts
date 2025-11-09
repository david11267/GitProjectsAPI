import { withClerkHandler } from 'svelte-clerk/server';

export const handle = withClerkHandler({
    publishableKey: import.meta.env.VITE_CLERK_PUBLISHABLE_KEY,
    secretKey: import.meta.env.VITE_CLERK_SECRET_KEY
});