<script lang="ts">
  import Pageable from '$lib/Pageable.svelte'
  import AddressTable from '$lib/inventory/addresses/AddressTable.svelte'
  import FeatureMenuBar from '$lib/FeatureMenuBar.svelte'
  import { Services } from '$lib/types/services.ts'
  import { genericStore } from '$lib/stores/genericStore.ts'

  let config = {
    title: 'Addresses',
    addButton: {
      url: '/inventory/addresses/add',
    },
    editButton: {
      url: '/inventory/addresses/edit',
      disabled: true,
    },
    deleteButton: {
      disabled: false,
    },
    delete: {
      endpoint: '/inventory/addresses',
      keys: ['inventory', 'addresses'],
    },
    service: Services.INVENTORY,
  }

  const updateConfig = () => {
    if ($genericStore.inventory === undefined) return

    if ($genericStore.inventory.addresses === undefined) {
      config.editButton.disabled = true
      return
    }

    if ($genericStore.inventory.addresses.length === 1) {
      config.editButton.url = `/inventory/addresses/${$genericStore.inventory.addresses[0].id}/edit`
      config.editButton.disabled = false
      config = config
      return
    }

    config.editButton.disabled = true
  }

  $: $genericStore, updateConfig()
</script>

<FeatureMenuBar {config} />
<div id="address" class="flex flex-col gap-3 px-10 pt-10">
  <Pageable endpoint="/inventory/addresses" component={AddressTable} />
</div>
