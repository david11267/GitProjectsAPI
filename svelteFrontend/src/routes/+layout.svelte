<script lang="ts">
	import '../app.css';
	import favicon from '$lib/assets/favicon.svg';
	import { ClerkProvider } from 'svelte-clerk';
	import { SignedIn, SignedOut, SignInButton, UserButton } from 'svelte-clerk';

	let { children } = $props();
</script>

<svelte:head>
	<link rel="icon" href={favicon} />
	<title>Dashboard</title>
</svelte:head>

<ClerkProvider publishableKey={import.meta.env.VITE_CLERK_PUBLISHABLE_KEY}>

<div class="bg-gray-900 min-h-screen w-full flex items-center justify-center">

		<SignedOut>
			<SignInButton />
		</SignedOut>
		<SignedIn>
			<div>
				<UserButton />
				{@render children()}
			</div>
		</SignedIn>
</div>
</ClerkProvider>

