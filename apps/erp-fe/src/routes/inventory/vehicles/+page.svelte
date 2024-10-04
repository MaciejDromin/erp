<script lang="ts">
  import VehicleTable from '$lib/inventory/vehicles/VehicleTable.svelte'
  import Pageable from '$lib/Pageable.svelte'
  import FeatureMenuBar from '$lib/FeatureMenuBar.svelte'

  let config = {
    title: 'Vehicles',
    addButton: {
      url: '/inventory/vehicles/add',
    },
    editButton: {
      disabled: false,
    },
    deleteButton: {
      disabled: false,
    },
  }
</script>

<FeatureMenuBar {config} />
<div id="vehicle" class="flex flex-col gap-3 px-10 pt-10">
  <Pageable endpoint="/inventory/vehicles" component={VehicleTable} />
</div>
