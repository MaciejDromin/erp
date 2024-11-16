<script lang="ts">
  import PropertyAddView from '$lib/inventory/properties/PropertyAddView.svelte'
  import type { PageData } from './$types'

  export let data: PageData
</script>

<div class="p-8">
  <h1 class="text-4xl font-bold text-black mb-8">Edit Property</h1>
  <PropertyAddView {data} />
</div>
