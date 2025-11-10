<script lang="ts">
	import Container from '../components/Container.svelte';

	export let data;
	const { apiKey } = data;
</script>

<div class="grid grid-cols-4 gap-4 text-white">
	{#if !apiKey}
		<Container>No API key available</Container>
	{:else}
		<Container><p>Api key: {apiKey.key}</p></Container>
		<Container><p>Remaining api calls {apiKey.quota}</p></Container>
		<Container class="col-span-2 row-span-2">
			<p>Api key usage timeline graph</p>
			<ul>
				{#each apiKey.timestamps as { action, timestamp }}
					<li>{action}: {timestamp}</li>
				{/each}
			</ul>
		</Container>
		<Container class="col-span-2">
			<p>Api key options</p>
			<label class="cursor-pointer" for="enableAi">EnableAi:</label>
			<input id="enableAi" bind:checked={apiKey.enableAi} type="checkbox" />
		</Container>
	{/if}
</div>

<div class="flex justify-center">
	<button class="m-4 cursor-pointer bg-gray-500 p-4">save changes</button>
</div>
