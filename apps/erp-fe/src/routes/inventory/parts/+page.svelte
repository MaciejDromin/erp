<script lang="ts">
  import PartsTable from '$lib/inventory/parts/PartsTable.svelte'
  import Pageable from '$lib/Pageable.svelte'
  import FeatureMenuBar from '$lib/FeatureMenuBar.svelte'
  import { Services } from '$lib/types/services.ts'
  import { genericStore } from '$lib/stores/genericStore.ts'

  let config = {
    title: 'Parts',
    addButton: {
      url: '/inventory/parts/add',
    },
    editButton: {
      url: '/inventory/parts/edit',
      disabled: true,
    },
    deleteButton: {
      disabled: false,
    },
    delete: {
      endpoint: '/parts',
      keys: ['inventory', 'parts'],
    },
    service: Services.INVENTORY,
  }

  const updateConfig = () => {
    if ($genericStore.inventory === undefined) return

    if ($genericStore.inventory.parts === undefined) {
      config.editButton.disabled = true
      return
    }

    if ($genericStore.inventory.parts.length === 1) {
      config.editButton.url = `/inventory/parts/${$genericStore.inventory.parts[0].id}/edit`
      config.editButton.disabled = false
      config = config
      return
    }

    config.editButton.disabled = true
  }

  $: $genericStore, updateConfig()
</script>

<FeatureMenuBar {config} />
<div id="part" class="flex flex-col gap-3 px-10 pt-10">
  <Pageable endpoint="/inventory/parts" component={PartsTable} />
</div>
