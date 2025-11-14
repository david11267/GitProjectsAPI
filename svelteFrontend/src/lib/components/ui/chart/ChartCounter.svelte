<script lang="ts">
	import * as Card from '$lib/components/ui/card/index.js';
	import * as Chart from '$lib/components/ui/chart/index.js';
	import { ArcChart, Text } from 'layerchart';
	import TrendingUpIcon from '@lucide/svelte/icons/trending-up';

	let { count, budget }: { count: number; budget: number } = $props();

	const chartData = [{ browser: 'safari', count: count, color: 'var(--color-safari)' }];

	const chartConfig = {
		count: { label: 'count' },
		safari: { label: 'Safari', color: 'var(--chart-2)' }
	} satisfies Chart.ChartConfig;
</script>

<Card.Root>
	<Card.Header class="items-center">
		<Card.Title>Api budget chart</Card.Title>
		<Card.Description>Showing total Api budget consumed</Card.Description>
	</Card.Header>
	<Card.Content class="flex-1">
		<Chart.Container config={chartConfig} class="mx-auto aspect-square max-h-[250px]">
			<ArcChart
				label="browser"
				value="count"
				outerRadius={-20}
				innerRadius={-12}
				padding={12}
				range={[0, -360]}
				maxValue={budget}
				cornerRadius={20}
				series={chartData.map((d) => ({
					key: d.browser,
					color: d.color,
					data: [d]
				}))}
				props={{
					arc: { track: { fill: 'var(--muted)' }, motion: 'tween' },
					tooltip: { context: { hideDelay: 350 } }
				}}
				tooltip={false}
			>
				{#snippet belowMarks()}
					<circle cx="0" cy="0" r="60" class="fill-background" />
				{/snippet}

				{#snippet aboveMarks()}
					<Text
						value={String(chartData[0].count)}
						textAnchor="middle"
						verticalAnchor="middle"
						class="fill-foreground text-4xl! font-bold"
						dy={3}
					/>
					<Text
						value="api calls left"
						textAnchor="middle"
						verticalAnchor="middle"
						class="fill-muted-foreground!"
						dy={22}
					/>
				{/snippet}
			</ArcChart>
		</Chart.Container>
	</Card.Content>
	<Card.Footer class="flex-col gap-2 text-sm">
		<div class="flex items-center gap-2 leading-none text-muted-foreground">
			Api budget refills every night at 00:00
		</div>
	</Card.Footer>
</Card.Root>
