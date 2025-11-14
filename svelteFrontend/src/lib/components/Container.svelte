<script lang="ts">
	import type { Snippet } from 'svelte';

	// destructure snippet props (children here) and class + other props
	let { children, class: className = '', ...rest } = $props();
</script>

<div class={`${className} `} {...rest}>
	{@render children?.()}
</div>
