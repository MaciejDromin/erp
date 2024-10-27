<script lang="ts">
  import Pageable from '$lib/Pageable.svelte'
  import CategoryTable from '$lib/inventory/categories/CategoryTable.svelte'
  import FeatureMenuBar from '$lib/FeatureMenuBar.svelte'
  import { Services } from '$lib/types/services.ts'
  import { genericStore } from '$lib/stores/genericStore.ts'

  let config = {
    title: 'Categories',
    addButton: {
      url: '/inventory/categories/add',
    },
    editButton: {
      url: '/inventory/categories/edit',
      disabled: true,
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

  const updateConfig = () => {
    if ($genericStore.inventory === undefined) return

    if (
      $genericStore.inventory.categories !== undefined &&
      $genericStore.inventory.categories.length > 0
    ) {
      config.editButton.url = `/inventory/categories/${$genericStore.inventory.categories[0].id}/edit`
      config.editButton.disabled = false
      config = config
    }
  }

  $: $genericStore, updateConfig()
</script>

<FeatureMenuBar {config} />
<div id="category" class="flex flex-col gap-3 px-10 pt-10">
  <Pageable endpoint="/inventory/categories" component={CategoryTable} />
</div>
