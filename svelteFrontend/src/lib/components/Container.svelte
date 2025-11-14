<script lang="ts">
	import type { Snippet } from 'svelte';

	// destructure snippet props (children here) and class + other props
	let { children, class: className = '', ...rest } = $props();
</script>

<div class={`p-4 shadow-2xl backdrop-blur-md backdrop-contrast-90 ${className}`} {...rest}>
	{@render children?.()}
</div>
