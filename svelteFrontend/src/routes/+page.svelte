<script lang="ts">
	import BarChartConsumtion from './../lib/components/ui/chart/BarChartConsumtion.svelte';
	import Container from '$lib/components/Container.svelte';
	import { Card } from '$lib/components/ui/card/index.js';
	import ChartCounter from '$lib/components/ui/chart/ChartCounter.svelte';
	import Options from '$lib/components/Options.svelte';
	import type { ApiKey } from '../types/AllTypes';

  let { data } = $props();
  let status = $state("Test your ApiKey here");
  
	const { apiKey }: { apiKey: ApiKey } = data;
	apiKey.timestamps.reverse();

	if (apiKey) {
		if (apiKey) {
			status = "✅ Your API key is valid";
		} else {
			status = "❌ No API key found please contact support";
		}
	}
</script>

<div class="grid grid-cols-4 gap-4 text-white">
		<Container class="col-span-4"
			><Card class=""
				>
				<p class="text-center font-bold">{status}</p>
				<div class="justify-center space-x-4 ">
				<strong><p>API Endpoint (GET)</p></strong>
				<a class="underline" href="https://githubapibackend.davidaslan.dev/api/projects?apiKey={apiKey.key}">https://githubapibackend.davidaslan.dev/api/projects?apiKey={apiKey.key}</a>
				<strong><p>Manual Ai Cache refresh (GET)</p></strong>
				<a class="underline" href="https://githubapibackend.davidaslan.dev/api/update?apiKey={apiKey.key}">https://githubapibackend.davidaslan.dev/api/update?apiKey={apiKey.key}</a>
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
	
</div>
