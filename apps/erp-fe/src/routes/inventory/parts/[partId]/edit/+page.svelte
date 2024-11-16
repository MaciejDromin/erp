<script lang="ts">
  import PartAddView from '$lib/inventory/parts/PartAddView.svelte'
  import type { PageData } from './$types'

  export let data: PageData
</script>

<div class="p-8">
  <h1 class="text-4xl font-bold text-black mb-8">Edit Part</h1>
  <PartAddView {data} />
</div>
