<script lang="ts">
	import Container from '$lib/components/Container.svelte';
	import ChartCounter from '$lib/components/ui/chart/ChartCounter.svelte';
	import type { ApiKey } from '../types/AllTypes.js';

	export let data;
	const { apiKey }: { apiKey: ApiKey } = data;
</script>

<div class="grid grid-cols-4 gap-4 text-white">
	{#if !apiKey}
		<Container>No API key available</Container>
	{:else}
		<Container class="col-span-2"><p>Api key: {apiKey.key}</p></Container>
		<Container class="col-span-2"><ChartCounter count={5} budget={10} /></Container>
		<Container class="col-span-2"><p>Remaining api calls {apiKey.quota}</p></Container>
		<Container class="col-span-4 row-span-2">
			<p>Api key usage timeline graph</p>
			<ul>
				{#each apiKey.timestamps as { action, timestamp }}
					<li>{action}: {timestamp}</li>
				{/each}
			</ul>
		</Container>
	{/if}
</div>
