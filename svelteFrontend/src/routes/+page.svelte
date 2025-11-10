<script lang="ts">
	import { useClerkContext } from 'svelte-clerk/client';
	import Container from '../components/Container.svelte';
	import type { ApiKey } from '../types/AllTypes';
	const ctx = useClerkContext();
	let showSaveButton = false;

	let apiKey: ApiKey = $state({
		id: '',
		key: '',
		quota: 0,
		issuedAt: '',
		timestamps: [],
		enableAi: true,
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
		<Container class="col-span-2 row-span-2"
			><p>Api key usage timeline graph</p>
			<ul>
				{#each apiKey.timestamps as { action, timestamp }}
					<li>{action} : {timestamp}</li>
				{/each}
			</ul></Container
		>
		<Container class="col-span-2"
			><p>Api key options</p>
			<div class="flex space-x-4">
				<div>
					<label class="cursor-pointer" for="enableAi">EnableAi:</label>
					<input
						class="cursor-pointer"
						id="enableAi"
						bind:checked={apiKey.enableAi}
						type="checkbox"
					/>
				</div>
			</div>
		</Container>
	{/if}
</div>
