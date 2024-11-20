<script lang="ts">
  import ItemTable from '$lib/inventory/items/ItemTable.svelte'
  import Pageable from '$lib/Pageable.svelte'
  import FeatureMenuBar from '$lib/FeatureMenuBar.svelte'
  import { Services } from '$lib/types/services.ts'
  import { genericStore } from '$lib/stores/genericStore.ts'
  import { apiRequest } from '$lib/scripts/uiHttpRequests.ts'
  import { HttpMethods } from '$lib/types/httpMethods.ts'

  let config = {
    title: 'Items',
    addButton: {
      url: '/inventory/items/add',
    },
    editButton: {
      url: '/inventory/items/edit',
      disabled: true,
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

  const updateConfig = () => {
    if ($genericStore.inventory === undefined) return

    if (
      $genericStore.inventory.items !== undefined &&
      $genericStore.inventory.items.length > 0
    ) {
      config.editButton.url = `/inventory/items/${$genericStore.inventory.items[0].id}/edit`
      config.editButton.disabled = false
      config = config
    } else {
      config.editButton.disabled = true
    }
  }

  const requestReport = async () => {
    await apiRequest('/reports', HttpMethods.POST, {
      endpoint: "inventory",
      name: "inventory",
      template: "inventory-report"
    })
  }

  $: $genericStore, updateConfig()
</script>

<FeatureMenuBar {config}>
  <button on:click={requestReport} class="btn btn-primary mr-3">Print</button>
</FeatureMenuBar>
<div id="item" class="flex flex-col gap-3 px-10 pt-10">
  <Pageable endpoint="/inventory/items" component={ItemTable} />
</div>
