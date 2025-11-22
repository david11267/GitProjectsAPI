<script lang="ts">
	import type { ApiKey } from '../../types/AllTypes';
	import Container from './Container.svelte';
	import { Card } from './ui/card';
	import * as Select from '$lib/components/ui/select/index.js';
	import Input from './ui/input/input.svelte';
	import { Button } from './ui/button';
	import { enhance } from '$app/forms'; // <--- IMPORT THIS

	const models = [
		{ value: 'gemini-2.5-flash', enabled: true },
		{ value: 'gemini-3-pro-preview', enabled: false },
		{ value: 'gpt-5', enabled: false }
	];

	let { apiKey }: { apiKey: ApiKey } = $props();

	// We use specific state variables for the UI
	let key = $state(apiKey.key);
	let aiModel = $state(apiKey.aiModel);
	let blacklist = $state(apiKey.blacklist);
	let whitelist = $state(apiKey.whitelist);

	let newBlacklistItem = $state('');
	let newWhitelistItem = $state('');

	const triggerContent = $derived(
		models.find((f) => f.value === aiModel)?.value ?? 'Select a model'
	);

	// Prepare the payload to be sent to the server
	// This automatically updates whenever aiModel, blacklist, or whitelist changes
	const payload = $derived(
		JSON.stringify({
			key,
			aiModel,
			blacklist,
			whitelist
		})
	);

	function addListItem(color: 'black' | 'white') {
		if (color === 'black') {
			const item = newBlacklistItem.trim();
			if (!item) return;
			blacklist.push(item);
			newBlacklistItem = '';
		}
		if (color === 'white') {
			const item = newWhitelistItem.trim();
			if (!item) return;
			whitelist.push(item);
			newWhitelistItem = '';
		}
	}

	function removeListItem(color: 'black' | 'white', item: string) {
		if (color === 'black') {
			blacklist = blacklist.filter((i) => i != item);
		}
		if (color === 'white') {
			whitelist = whitelist.filter((i) => i != item);
		}
	}
</script>

<Container class="col-span-2">
	<Card class="p-4">
		<h2>Options</h2>
		<Select.Root type="single" name="aiModel" bind:value={aiModel}>
			<Select.Trigger class="w-auto">
				{triggerContent}
			</Select.Trigger>
			<Select.Content>
				<Select.Group>
					<Select.Label>Models</Select.Label>
					{#each models as model (model.value)}
						<Select.Item value={model.value} label={model.value} disabled={!model.enabled}>
							{model.value}
						</Select.Item>
					{/each}
				</Select.Group>
			</Select.Content>
		</Select.Root>

		<div class="mt-4 flex justify-evenly space-x-4 transition-all">
			<div>
				<Input
					type="text"
					placeholder="Add to blacklist"
					bind:value={newBlacklistItem}
					onkeydown={(e) => e.key === 'Enter' && addListItem('black')}
					class="max-w-xs"
				/>
				<div class="flex flex-col gap-1 p-2">
					{#each blacklist as blackItem}
						<button
							class="cursor-pointer text-left hover:text-red-500 hover:line-through"
							onclick={() => removeListItem('black', blackItem)}
							type="button"
						>
							{blackItem}
						</button>
					{/each}
				</div>
			</div>

			<div>
				<Input
					type="text"
					placeholder="Add to whitelist"
					class="max-w-xs"
					bind:value={newWhitelistItem}
					onkeydown={(e) => e.key === 'Enter' && addListItem('white')}
				/>
				<div class="flex flex-col gap-1 p-2">
					{#each whitelist as whiteItem}
						<button
							class="cursor-pointer text-left hover:text-red-500 hover:line-through"
							onclick={() => removeListItem('white', whiteItem)}
							type="button"
						>
							{whiteItem}
						</button>
					{/each}
				</div>
			</div>
		</div>

		<form method="POST" action="?/update" use:enhance>
			<input type="hidden" name="optionsPayload" value={payload} />

			<Button class="cursor-pointer" type="submit">Update options</Button>
		</form>
	</Card>
</Container>
