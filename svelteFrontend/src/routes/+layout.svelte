<script lang="ts">
	import '../app.css';
	import favicon from '$lib/assets/favicon.svg';
	import { ClerkProvider, SignIn } from 'svelte-clerk';
	import { SignedIn, SignedOut, SignInButton, UserButton } from 'svelte-clerk';

	let { children } = $props();
</script>

<svelte:head>
	<link rel="icon" href={favicon} />
	<title>Dashboard</title>
</svelte:head>

<ClerkProvider publishableKey={import.meta.env.VITE_CLERK_PUBLISHABLE_KEY}>
	<div class="flex min-h-screen w-full items-center justify-center bg-gray-900">
		<SignedOut>
			<SignIn />
		</SignedOut>
		<SignedIn>
			<div>
				<UserButton />
				{@render children()}
			</div>
		</SignedIn>
	</div>
</ClerkProvider>
