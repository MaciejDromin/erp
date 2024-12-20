<script lang="ts">
  import VehicleAddView from '$lib/inventory/vehicles/VehicleAddView.svelte'
  import type { PageData } from './$types'

  export let data: PageData
  export let form
</script>

<div class="p-8">
  <h1 class="text-4xl font-bold text-black mb-8">Edit Vehicle</h1>
  <VehicleAddView {data} {form} />
</div>
