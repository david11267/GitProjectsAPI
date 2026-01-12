<script lang="ts">
	import BarChartConsumtion from './../lib/components/ui/chart/BarChartConsumtion.svelte';
	import Container from '$lib/components/Container.svelte';
	import { Card, Root } from '$lib/components/ui/card/index.js';
	import ChartCounter from '$lib/components/ui/chart/ChartCounter.svelte';
	import type { ApiKey } from '../types/AllTypes.js';
	import Options from '$lib/components/Options.svelte';

	export let data;
	const { apiKey }: { apiKey: ApiKey } = data;
	apiKey.timestamps.reverse();
</script>

<div class="grid grid-cols-4 gap-4 text-white">
	{#if !apiKey}
		<Container class="col-span-4">
			<Card><h1 class=" text-red-500">No api key available</h1></Card>
		</Container>
	{:else}
		<Container class="col-span-4"
			><Card
				><h1 class="text-center text-xl">Api key</h1>
				<p class="text-center font-bold">{apiKey.key}</p>
				<div class="flex justify-center mx-4">
					<strong>API Endpoint (GET)</strong>
				<a class="hover:underline" href="https://githubapibackend.davidaslan.dev/api/projects?apiKey={apiKey.key}">https://githubapibackend.davidaslan.dev/api/projects?apiKey={apiKey.key}</a>
				</div>
			</Card></Container
		>
		<Container class="col-span-2"><ChartCounter count={apiKey.quota} budget={10} /></Container>
		<Options {apiKey} />
		<Container class="col-span-4">
			<BarChartConsumtion timestamps={apiKey.timestamps} />
		</Container>

		<Container class="col-span-4">
			<Card class=" max-h-[500px] overflow-y-scroll  font-mono">
				<h2>Api key usage timeline graph</h2>
				<l1 class="p-4">
					{#each apiKey.timestamps as { action, timestamp }}
						<li>{action}: {timestamp}</li>
					{/each}
				</l1>
			</Card>
		</Container>
	{/if}
</div>
