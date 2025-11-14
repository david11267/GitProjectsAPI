<script lang="ts">
	import Container from '$lib/components/Container.svelte';

	export let data;
	const { apiKey } = data;
</script>

<div class="grid grid-cols-4 gap-4 text-white">
	{#if !apiKey}
		<Container>No API key available</Container>
	{:else}
		<Container class="col-span-2"><p>Api key: {apiKey.key}</p></Container>
		<Container class="col-span-2"><p>Api key: {apiKey.key}</p></Container>
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
