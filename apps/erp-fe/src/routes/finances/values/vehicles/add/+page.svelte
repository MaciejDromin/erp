<script lang="ts">
  import ObjectValueAddView from '$lib/finances/object-value/ObjectValueAddView.svelte'
  import VehicleTable from '$lib/inventory/vehicles/VehicleTable.svelte'
  import type { PageData } from './$types'

  export let data: PageData

  const config = {
    data: data,
    component: VehicleTable,
    endpoint: '/inventory/vehicles',
    buttonName: 'vehicle',
    storeKey: 'vehicles',
  }
</script>

<div class="p-8">
  <h1 class="text-4xl font-bold text-black mb-8">Add Vehicle Value</h1>
  <ObjectValueAddView {config} />
</div>
