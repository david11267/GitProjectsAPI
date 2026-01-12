<script lang="ts">
	import { options } from './../../.svelte-kit/generated/server/internal.js';
	import '../app.css';
	import favicon from '$lib/assets/favicon.svg';
	import { ClerkProvider, SignIn } from 'svelte-clerk';
	import { SignedIn, SignedOut, SignInButton, UserButton } from 'svelte-clerk';
	import Card from '$lib/components/ui/card/card.svelte';

	let { children } = $props();
</script>

<svelte:head>
	<link rel="icon" href={favicon} />
	<title>Dashboard</title>
</svelte:head>

<ClerkProvider publishableKey={import.meta.env.VITE_CLERK_PUBLISHABLE_KEY}>
	<div class="flex min-h-screen w-full items-center justify-center bg-black">
		<SignedOut>
			<div class="flex flex-wrap gap-6 justify-center p-4">
				<Card class="w-xl p-8">
					<h1>Welcome to the GitProjects API Dashboard</h1>

					<div class="flex justify-center">
						<svg xmlns="http://www.w3.org/2000/svg" width="32" height="32" viewBox="0 0 24 24"
							><g
								fill="none"
								stroke="currentColor"
								stroke-dasharray="28"
								stroke-linecap="round"
								stroke-linejoin="round"
								stroke-width="2"
								><path d="M4 21v-1c0 -3.31 2.69 -6 6 -6h4c3.31 0 6 2.69 6 6v1"
									><animate
										fill="freeze"
										attributeName="stroke-dashoffset"
										dur="0.4s"
										values="28;0"
									/></path
								><path
									stroke-dashoffset="28"
									d="M12 11c-2.21 0 -4 -1.79 -4 -4c0 -2.21 1.79 -4 4 -4c2.21 0 4 1.79 4 4c0 2.21 -1.79 4 -4 4Z"
									><animate
										fill="freeze"
										attributeName="stroke-dashoffset"
										begin="0.4s"
										dur="0.4s"
										to="0"
									/></path
								></g
							></svg
						>
						<svg xmlns="http://www.w3.org/2000/svg" width="32" height="32" viewBox="0 0 24 24"
							><g
								fill="none"
								stroke="currentColor"
								stroke-linecap="round"
								stroke-linejoin="round"
								stroke-width="2"
								><path stroke-dasharray="20" d="M3 12h17.5"
									><animate
										fill="freeze"
										attributeName="stroke-dashoffset"
										dur="0.3s"
										values="20;0"
									/></path
								><path stroke-dasharray="12" stroke-dashoffset="12" d="M21 12l-7 7M21 12l-7 -7"
									><animate
										fill="freeze"
										attributeName="stroke-dashoffset"
										begin="0.3s"
										dur="0.2s"
										to="0"
									/></path
								></g
							></svg
						>
						<svg xmlns="http://www.w3.org/2000/svg" width="32" height="32" viewBox="0 0 24 24"
							><g
								fill="none"
								stroke="#000000"
								stroke-linecap="round"
								stroke-linejoin="round"
								stroke-width="2"
								><path
									stroke-dasharray="32"
									d="M12 4c1.67 0 2.61 0.4 3 0.5c0.53 -0.43 1.94 -1.5 3.5 -1.5c0.34 1 0.29 2.22 0 3c0.75 1 1 2 1 3.5c0 2.19 -0.48 3.58 -1.5 4.5c-1.02 0.92 -2.11 1.37 -3.5 1.5c0.65 0.54 0.5 1.87 0.5 2.5c0 0.73 0 3 0 3M12 4c-1.67 0 -2.61 0.4 -3 0.5c-0.53 -0.43 -1.94 -1.5 -3.5 -1.5c-0.34 1 -0.29 2.22 0 3c-0.75 1 -1 2 -1 3.5c0 2.19 0.48 3.58 1.5 4.5c1.02 0.92 2.11 1.37 3.5 1.5c-0.65 0.54 -0.5 1.87 -0.5 2.5c0 0.73 0 3 0 3"
									><animate
										fill="freeze"
										attributeName="stroke-dashoffset"
										dur="0.6s"
										values="32;0"
									/></path
								><path
									stroke-dasharray="10"
									stroke-dashoffset="10"
									d="M9 19c-1.41 0 -2.84 -0.56 -3.69 -1.19c-0.84 -0.63 -1.09 -1.66 -2.31 -2.31"
									><animate
										fill="freeze"
										attributeName="stroke-dashoffset"
										begin="0.7s"
										dur="0.2s"
										to="0"
									/></path
								></g
							></svg
						>
						<svg xmlns="http://www.w3.org/2000/svg" width="32" height="32" viewBox="0 0 24 24"
							><g
								fill="none"
								stroke="currentColor"
								stroke-linecap="round"
								stroke-linejoin="round"
								stroke-width="2"
								><path stroke-dasharray="20" d="M3 12h17.5"
									><animate
										fill="freeze"
										attributeName="stroke-dashoffset"
										dur="0.3s"
										values="20;0"
									/></path
								><path stroke-dasharray="12" stroke-dashoffset="12" d="M21 12l-7 7M21 12l-7 -7"
									><animate
										fill="freeze"
										attributeName="stroke-dashoffset"
										begin="0.3s"
										dur="0.2s"
										to="0"
									/></path
								></g
							></svg
						>
						<svg xmlns="http://www.w3.org/2000/svg" width="32" height="32" viewBox="0 0 24 24"
							><path
								fill="currentColor"
								d="M19.43 12.98c.04-.32.07-.64.07-.98s-.03-.66-.07-.98l2.11-1.65c.19-.15.24-.42.12-.64l-2-3.46c-.12-.22-.39-.3-.61-.22l-2.49 1c-.52-.4-1.08-.73-1.69-.98l-.38-2.65C14.46 2.18 14.25 2 14 2h-4c-.25 0-.46.18-.49.42l-.38 2.65c-.61.25-1.17.59-1.69.98l-2.49-1c-.23-.09-.49 0-.61.22l-2 3.46c-.13.22-.07.49.12.64l2.11 1.65c-.04.32-.07.65-.07.98s.03.66.07.98l-2.11 1.65c-.19.15-.24.42-.12.64l2 3.46c.12.22.39.3.61.22l2.49-1c.52.4 1.08.73 1.69.98l.38 2.65c.03.24.24.42.49.42h4c.25 0 .46-.18.49-.42l.38-2.65c.61-.25 1.17-.59 1.69-.98l2.49 1c.23.09.49 0 .61-.22l2-3.46c.12-.22.07-.49-.12-.64l-2.11-1.65zM12 15.5c-1.93 0-3.5-1.57-3.5-3.5s1.57-3.5 3.5-3.5s3.5 1.57 3.5 3.5s-1.57 3.5-3.5 3.5z"
								><animateTransform
									attributeName="transform"
									attributeType="XML"
									dur="10s"
									from="0 12 12"
									repeatCount="indefinite"
									to="360 12 12"
									type="rotate"
								/></path
							></svg
						>
						<svg xmlns="http://www.w3.org/2000/svg" width="32" height="32" viewBox="0 0 24 24"
							><g
								fill="none"
								stroke="currentColor"
								stroke-linecap="round"
								stroke-linejoin="round"
								stroke-width="2"
								><path stroke-dasharray="20" d="M3 12h17.5"
									><animate
										fill="freeze"
										attributeName="stroke-dashoffset"
										dur="0.3s"
										values="20;0"
									/></path
								><path stroke-dasharray="12" stroke-dashoffset="12" d="M21 12l-7 7M21 12l-7 -7"
									><animate
										fill="freeze"
										attributeName="stroke-dashoffset"
										begin="0.3s"
										dur="0.2s"
										to="0"
									/></path
								></g
							></svg
						>
						<svg xmlns="http://www.w3.org/2000/svg" width="32" height="32" viewBox="0 0 24 24"
							><defs
								><mask id="SVGg6iutdra"
									><g stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
										><path
											fill="#fff"
											fill-opacity="0"
											stroke="#fff"
											stroke-dasharray="62"
											d="M13.5 3l5.5 5.5v11.5c0 0.55 -0.45 1 -1 1h-12c-0.55 0 -1 -0.45 -1 -1v-16c0 -0.55 0.45 -1 1 -1Z"
											><animate
												fill="freeze"
												attributeName="stroke-dashoffset"
												dur="0.6s"
												values="62;0"
											/><animate
												fill="freeze"
												attributeName="fill-opacity"
												begin="0.6s"
												dur="0.4s"
												to="1"
											/></path
										><path stroke="#000" d="M14 3.5l0 4.5l4.5 0Z" opacity="0"
											><set fill="freeze" attributeName="opacity" begin="0.6s" to="1" /><animate
												fill="freeze"
												attributeName="d"
												begin="0.6s"
												dur="0.2s"
												values="M14 3.5l2.25 2.25l2.25 2.25Z;M14 3.5l0 4.5l4.5 0Z"
											/></path
										></g
									></mask
								></defs
							><path fill="#000000" d="M0 0h24v24H0z" mask="url(#SVGg6iutdra)" /><path
								fill="none"
								stroke="#000000"
								stroke-linecap="round"
								stroke-linejoin="round"
								stroke-width="2"
								d="M13.5 3l5.5 5.5"
								opacity="0"><set fill="freeze" attributeName="opacity" begin="0.6s" to="1" /></path
							></svg
						>
					</div>

					<ol class="mt-4 list-inside list-decimal space-y-2">
						<li>Log in with your account to access your personal API keys.</li>
						<li>The dashboard grants you programmatic access to our GitHub analysis API.</li>
						<li>
							The API points to all of your public GitHub repositories and can filter out unwanted
							ones in the options.
						</li>
						<li>The API analyzes your repository structure, code, and metadata.</li>
						<li>It generates well-structured project descriptions automatically.</li>
						<li>Detected technologies and frameworks are highlighted.</li>
						<li>Icons for each technology are included for easy visualization.</li>
						<li>Relevant links (GitHub, documentation, or resources) are added.</li>
						<li>All output is ready to use in documentation, portfolios, or dashboards.</li>
						<li>Monitor your API usage, limits, and keys directly from this dashboard.</li>
					</ol>
				</Card>
				<SignIn />
			</div>
		</SignedOut>
		<SignedIn>
			<div>
				<UserButton />
				{@render children()}
			</div>
		</SignedIn>
	</div>
</ClerkProvider>
