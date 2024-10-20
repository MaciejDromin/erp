<script lang="ts">
  import MaintenanceTable from '$lib/inventory/maintenance/MaintenanceTable.svelte'
  import Pageable from '$lib/Pageable.svelte'
  import FeatureMenuBar from '$lib/FeatureMenuBar.svelte'
  import { Services } from '$lib/types/services.ts'

  let config = {
    title: 'Maintenance',
    addButton: {
      url: '/inventory/maintenance/add',
    },
    editButton: {
      disabled: false,
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
</script>

<FeatureMenuBar {config} />
<div id="maintenance" class="flex flex-col gap-3 px-10 pt-10">
  <Pageable endpoint="/inventory/maintenance" component={MaintenanceTable} />
</div>
