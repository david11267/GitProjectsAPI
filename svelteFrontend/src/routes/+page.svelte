<script lang="ts">
	import { useClerkContext } from 'svelte-clerk/client';
	import Container from '../components/Container.svelte';
	import type { ApiKey } from '../types/AllTypes';
	const ctx = useClerkContext();

	let apiKey: ApiKey = $state({
		id: '',
		key: '',
		quota: 0,
		issuedAt: '',
		user: undefined
	});
	const fetchDataFromExternalResource = async (token: string) => {
		const baseUrl = import.meta.env.VITE_API_BASE_URL;
		const response = await fetch(`${baseUrl}/api/key`, {
			headers: {
				Authorization: `Bearer ${token}`
			}
		});
		return response.json();
	};

	$effect(() => {
		// Fetch data when the component is initialized
		const run = async () => {
			const token = await ctx.session?.getToken();
			if (!token) return;

			apiKey = (await fetchDataFromExternalResource(token)) as ApiKey;
		};

		run();
	});
</script>

<div class="grid grid-cols-4 gap-4 text-white">
	{#if !apiKey}
		<Container>
			No API key available
			<p></p>
		</Container>
	{:else}
		<Container>
			<p>Api key: {apiKey.key}</p>
		</Container>
		<Container>
			<p>Remaining api calls {apiKey.quota}</p>
		</Container>
		<Container class="col-span-2">Api key usage timeline graph</Container>
		<Container class="col-span-2">Api key options</Container>
	{/if}
</div>
