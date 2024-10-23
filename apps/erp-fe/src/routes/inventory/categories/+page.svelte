<script lang="ts">
  import Pageable from '$lib/Pageable.svelte'
  import CategoryTable from '$lib/inventory/categories/CategoryTable.svelte'
  import FeatureMenuBar from '$lib/FeatureMenuBar.svelte'
  import { Services } from '$lib/types/services.ts'

  let config = {
    title: 'Categories',
    addButton: {
      url: '/inventory/categories/add',
    },
    editButton: {
      disabled: false,
    },
    deleteButton: {
      disabled: false,
    },
    delete: {
      endpoint: '/inventory/categories',
      keys: ['inventory', 'categories'],
    },
    service: Services.INVENTORY,
  }
</script>

<FeatureMenuBar {config} />
<div id="category" class="flex flex-col gap-3 px-10 pt-10">
  <Pageable endpoint="/inventory/categories" component={CategoryTable} />
</div>
