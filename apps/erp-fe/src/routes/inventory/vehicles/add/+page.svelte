<script>
  import VehicleAddView from '$lib/inventory/vehicles/VehicleAddView.svelte'

  export let form
</script>

<div class="p-8">
  <h1 class="text-4xl font-bold text-black mb-8">Add Vehicle</h1>
  <VehicleAddView {form} />
</div>
