<script lang="ts">
  import VehicleTable from '$lib/inventory/vehicles/VehicleTable.svelte'
  import Pageable from '$lib/Pageable.svelte'
  import FeatureMenuBar from '$lib/FeatureMenuBar.svelte'
  import { Services } from '$lib/types/services.ts'
  import { genericStore } from '$lib/stores/genericStore.ts'

  let config = {
    title: 'Vehicles',
    addButton: {
      url: '/inventory/vehicles/add',
    },
    editButton: {
      url: '/inventory/vehicles/edit',
      disabled: true,
    },
    deleteButton: {
      disabled: false,
    },
    delete: {
      endpoint: '/vehicles',
      keys: ['inventory', 'vehicles'],
    },
    service: Services.INVENTORY,
  }

  const updateConfig = () => {
    if ($genericStore.inventory === undefined) return

    if ($genericStore.inventory.vehicles === undefined) {
      config.editButton.disabled = true
      return
    }

    if ($genericStore.inventory.vehicles.length === 1) {
      config.editButton.url = `/inventory/vehicles/${$genericStore.inventory.vehicles[0].id}/edit`
      config.editButton.disabled = false
      config = config
      return
    }

    config.editButton.disabled = true
  }

  $: $genericStore, updateConfig()
</script>

<FeatureMenuBar {config} />
<div id="vehicle" class="flex flex-col gap-3 px-10 pt-10">
  <Pageable endpoint="/inventory/vehicles" component={VehicleTable} />
</div>
