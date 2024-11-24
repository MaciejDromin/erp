<script lang="ts">
  import ContractorsTable from '$lib/inventory/contractors/ContractorsTable.svelte'
  import Pageable from '$lib/Pageable.svelte'
  import FeatureMenuBar from '$lib/FeatureMenuBar.svelte'
  import { Services } from '$lib/types/services.ts'
  import { genericStore } from '$lib/stores/genericStore.ts'

  let config = {
    title: 'Contractors',
    addButton: {
      url: '/inventory/contractors/add',
    },
    editButton: {
      url: '/inventory/contractors/edit',
      disabled: true,
    },
    deleteButton: {
      disabled: false,
    },
    delete: {
      endpoint: '/contractors',
      keys: ['inventory', 'contractors'],
    },
    service: Services.INVENTORY,
  }

  const updateConfig = () => {
    if ($genericStore.inventory === undefined) return

    if ($genericStore.inventory.contractors === undefined) {
      config.editButton.disabled = true
      return
    }

    if ($genericStore.inventory.contractors.length === 1) {
      config.editButton.url = `/inventory/contractors/${$genericStore.inventory.contractors[0].id}/edit`
      config.editButton.disabled = false
      config = config
      return
    }

    config.editButton.disabled = true
  }

  $: $genericStore, updateConfig()
</script>

<FeatureMenuBar {config} />
<div id="contractor" class="flex flex-col gap-3 px-10 pt-10">
  <Pageable endpoint="/inventory/contractors" component={ContractorsTable} />
</div>
