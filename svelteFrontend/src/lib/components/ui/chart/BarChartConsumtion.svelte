<script lang="ts">
	import { scaleBand } from 'd3-scale';
	import { BarChart, type ChartContextValue } from 'layerchart';
	import TrendingUpIcon from '@lucide/svelte/icons/trending-up';
	import * as Chart from '$lib/components/ui/chart/index.js';
	import * as Card from '$lib/components/ui/card/index.js';
	import { cubicInOut } from 'svelte/easing';
	import type { Timestamp } from '../../../../types/AllTypes';

	let { timestamps }: { timestamps: Array<Timestamp> } = $props();

	let apiCalls = timestamps.filter((t) => t.action.includes('API handled projects request'));
	apiCalls = apiCalls.map((t) => ({
		...t,
		timestamp: new Date(t.timestamp)
	}));

	const chartData = [
		{
			month: 'January',
			consumed: apiCalls.filter((t) => t.timestamp.getMonth() === 0).length
		},
		{ month: 'February', consumed: apiCalls.filter((t) => t.timestamp.getMonth() === 1).length },
		{ month: 'March', consumed: apiCalls.filter((t) => t.timestamp.getMonth() === 2).length },
		{ month: 'April', consumed: apiCalls.filter((t) => t.timestamp.getMonth() === 3).length },
		{ month: 'May', consumed: apiCalls.filter((t) => t.timestamp.getMonth() === 4).length },
		{ month: 'June', consumed: apiCalls.filter((t) => t.timestamp.getMonth() === 5).length },
		{ month: 'July', consumed: apiCalls.filter((t) => t.timestamp.getMonth() === 6).length },
		{ month: 'August', consumed: apiCalls.filter((t) => t.timestamp.getMonth() === 7).length },
		{ month: 'September', consumed: apiCalls.filter((t) => t.timestamp.getMonth() === 8).length },
		{ month: 'October', consumed: apiCalls.filter((t) => t.timestamp.getMonth() === 9).length },
		{ month: 'November', consumed: apiCalls.filter((t) => t.timestamp.getMonth() === 10).length },
		{ month: 'December', consumed: apiCalls.filter((t) => t.timestamp.getMonth() === 11).length }
	];

	const chartConfig = {
		consumed: { label: 'consumed', color: 'var(--chart-1)' }
	} satisfies Chart.ChartConfig;

	let context = $state<ChartContextValue>();
</script>

<Card.Root>
	<Card.Header>
		<Card.Title>Yearly consumption</Card.Title>
		<Card.Description>Yearly consumption total: {apiCalls.length}</Card.Description>
	</Card.Header>
	<Card.Content>
		<Chart.Container config={chartConfig}>
			<BarChart
				labels={{ offset: 12 }}
				tooltip={false}
				bind:context
				data={chartData}
				xScale={scaleBand().padding(0.25)}
				x="month"
				axis="x"
				series={[{ key: 'consumed', label: 'consumed', color: chartConfig.consumed.color }]}
				props={{
					bars: {
						stroke: 'none',
						rounded: 'all',
						radius: 8,
						// use the height of the chart to animate the bars
						initialY: context?.height,
						initialHeight: 0,
						motion: {
							x: { type: 'tween', duration: 500, easing: cubicInOut },
							width: { type: 'tween', duration: 500, easing: cubicInOut },
							height: { type: 'tween', duration: 500, easing: cubicInOut },
							y: { type: 'tween', duration: 500, easing: cubicInOut }
						}
					},
					highlight: { area: { fill: 'none' } },
					xAxis: { format: (d) => d.slice(0, 3) }
				}}
			></BarChart>
		</Chart.Container>
	</Card.Content>
	<Card.Footer>
		<div class="flex w-full items-start gap-2 text-sm">
			<div class="grid gap-2">
				<div class="flex items-center gap-2 leading-none text-muted-foreground">
					Showing total consumption for the whole year
				</div>
			</div>
		</div>
	</Card.Footer>
</Card.Root>
