<script lang="ts">
  import PropertyTable from '$lib/inventory/properties/PropertyTable.svelte'
  import Pageable from '$lib/Pageable.svelte'
  import FeatureMenuBar from '$lib/FeatureMenuBar.svelte'
  import { Services } from '$lib/types/services.ts'

  let config = {
    title: 'Properties',
    addButton: {
      url: '/inventory/properties/add',
    },
    editButton: {
      disabled: false,
    },
    deleteButton: {
      disabled: false,
    },
    delete: {
      endpoint: '/properties',
      keys: ['inventory', 'properties'],
    },
    service: Services.INVENTORY,
  }
</script>

<FeatureMenuBar {config} />
<div id="property" class="flex flex-col gap-3 px-10 pt-10">
  <Pageable endpoint="/inventory/properties" component={PropertyTable} />
</div>
