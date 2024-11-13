<script lang="ts">
  import MaintenanceTable from '$lib/inventory/maintenance/MaintenanceTable.svelte'
  import Pageable from '$lib/Pageable.svelte'
  import FeatureMenuBar from '$lib/FeatureMenuBar.svelte'
  import { Services } from '$lib/types/services.ts'
  import { genericStore } from '$lib/stores/genericStore.ts'

  let config = {
    title: 'Maintenance',
    addButton: {
      url: '/inventory/maintenance/add',
    },
    editButton: {
      url: '/inventory/maintenance/edit',
      disabled: true,
    },
    deleteButton: {
      disabled: false,
    },
    delete: {
      endpoint: '/maintenance',
      keys: ['inventory', 'maintenance'],
    },
    service: Services.INVENTORY,
  }

  const updateConfig = () => {
    if ($genericStore.inventory === undefined) return

    if (
      $genericStore.inventory.maintenance !== undefined &&
      $genericStore.inventory.maintenance.length > 0
    ) {
      config.editButton.url = `/inventory/maintenance/${$genericStore.inventory.maintenance[0].id}/edit`
      config.editButton.disabled = false
      config = config
    } else {
      config.editButton.disabled = true
    }
  }

  $: $genericStore, updateConfig()
</script>

<FeatureMenuBar {config} />
<div id="maintenance" class="flex flex-col gap-3 px-10 pt-10">
  <Pageable endpoint="/inventory/maintenance" component={MaintenanceTable} />
</div>
