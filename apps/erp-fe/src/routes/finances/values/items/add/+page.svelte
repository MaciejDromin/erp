<script lang='ts'>
  import ObjectValueAddView from '$lib/finances/object-value/ObjectValueAddView.svelte'
  import type { PageData } from './$types'

  export let data:PageData
</script>

<div class="p-8">
  <h1 class="text-4xl font-bold text-black mb-8">Add Item Value</h1>
  <ObjectValueAddView {data} />
</div>
