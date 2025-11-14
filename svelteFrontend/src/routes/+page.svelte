<script lang="ts">
	import Container from '$lib/components/Container.svelte';
	import { Card } from '$lib/components/ui/card/index.js';
	import ChartCounter from '$lib/components/ui/chart/ChartCounter.svelte';
	import type { ApiKey } from '../types/AllTypes.js';

	export let data;
	const { apiKey }: { apiKey: ApiKey } = data;
</script>

<div class="grid grid-cols-4 gap-4 text-white">
	{#if !apiKey}
		<Container class="col-span-4">
			<Card class=" text-center text-2xl text-red-500"><h1>No api key available</h1></Card>
		</Container>
	{:else}
		<Container class="col-span-4"
			><Card
				><h1 class="text-center text-xl">Api key</h1>
				<p class="text-center font-bold">{apiKey.key}</p></Card
			></Container
		>
		<Container class="col-span-2"><ChartCounter count={5} budget={10} /></Container>
		<Container class="col-span-2"><Card>OPTIONS</Card></Container>
		<Container class="col-span-4"><Card>USAGE GRAPH</Card></Container>

		<Container class="col-span-4">
			<Card class=" h-[404px] overflow-y-scroll  font-mono">
				<p>Api key usage timeline graph</p>
				<ul>
					{#each apiKey.timestamps as { action, timestamp }}
						<li>{action}: {timestamp}</li>
					{/each}
				</ul>
			</Card>
		</Container>
	{/if}
</div>
