<script lang="ts">
  import ItemAddView from '$lib/inventory/items/ItemAddView.svelte'
  import type { PageData } from './$types'

  export let data: PageData
</script>

<div class="p-8">
  <h1 class="text-4xl font-bold text-black mb-8">Edit Item</h1>
  <ItemAddView {data} />
</div>
