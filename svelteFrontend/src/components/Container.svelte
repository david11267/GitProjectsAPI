<script lang="ts">
  import type { Snippet } from "svelte";

  // destructure snippet props (children here) and class + other props
  let { children, class: className = '', ...rest } = $props();
</script>

<div
  class={`backdrop-contrast-90 backdrop-blur-md p-4 shadow-2xl ${className}`}
  {...rest}
>
  {@render children?.()}
</div>
