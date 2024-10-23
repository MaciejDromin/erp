<script lang="ts">
  import ItemTable from '$lib/inventory/items/ItemTable.svelte'
  import Pageable from '$lib/Pageable.svelte'
  import FeatureMenuBar from '$lib/FeatureMenuBar.svelte'
  import { Services } from '$lib/types/services.ts'

  let config = {
    title: 'Items',
    addButton: {
      url: '/inventory/items/add',
    },
    editButton: {
      disabled: false,
    },
    deleteButton: {
      disabled: false,
    },
    delete: {
      endpoint: '/inventory/items',
      keys: ['inventory', 'items'],
    },
    service: Services.INVENTORY,
  }
</script>

<FeatureMenuBar {config} />
<div id="item" class="flex flex-col gap-3 px-10 pt-10">
  <Pageable endpoint="/inventory/items" component={ItemTable} />
</div>
