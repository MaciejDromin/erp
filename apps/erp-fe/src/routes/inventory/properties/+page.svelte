<script lang="ts">
  import PropertyTable from '$lib/inventory/properties/PropertyTable.svelte'
  import Pageable from '$lib/Pageable.svelte'
  import FeatureMenuBar from '$lib/FeatureMenuBar.svelte'
  import { Services } from '$lib/types/services.ts'
  import { genericStore } from '$lib/stores/genericStore.ts'

  let config = {
    title: 'Properties',
    addButton: {
      url: '/inventory/properties/add',
    },
    editButton: {
      url: '/inventory/properties/edit',
      disabled: true,
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

  const updateConfig = () => {
    if ($genericStore.inventory === undefined) return

    if ($genericStore.inventory.properties === undefined) {
      config.editButton.disabled = true
      return
    }

    if ($genericStore.inventory.properties.length === 1) {
      config.editButton.url = `/inventory/properties/${$genericStore.inventory.properties[0].id}/edit`
      config.editButton.disabled = false
      config = config
      return
    }

    config.editButton.disabled = true
  }

  $: $genericStore, updateConfig()
</script>

<FeatureMenuBar {config} />
<div id="property" class="flex flex-col gap-3 px-10 pt-10">
  <Pageable endpoint="/inventory/properties" component={PropertyTable} />
</div>
