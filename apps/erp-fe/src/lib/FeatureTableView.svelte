<script lang="ts">
  import Pageable from '$lib/Pageable.svelte'

  export let config
</script>

<div id={config.id} class="flex flex-col gap-3 px-10 pt-10">
  <slot />
  <Pageable endpoint={config.endpoint} component={config.component} />
</div>
