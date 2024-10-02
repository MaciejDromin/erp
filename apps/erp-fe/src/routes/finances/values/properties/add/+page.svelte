<script lang="ts">
  import ObjectValueAddView from '$lib/finances/object-value/ObjectValueAddView.svelte'
  import PropertyTable from '$lib/inventory/properties/PropertyTable.svelte'
  import type { PageData } from './$types'

  export let data: PageData

  const config = {
    data: data,
    component: PropertyTable,
    endpoint: '/inventory/properties',
    buttonName: 'property',
    storeKey: 'properties',
  }
</script>

<div class="p-8">
  <h1 class="text-4xl font-bold text-black mb-8">Add Property Value</h1>
  <ObjectValueAddView {config} />
</div>
