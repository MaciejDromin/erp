<script lang="ts">
  import Pageable from '$lib/Pageable.svelte'
  import AddressTable from '$lib/inventory/addresses/AddressTable.svelte'
  import FeatureMenuBar from '$lib/FeatureMenuBar.svelte'
  import { Services } from '$lib/types/services.ts'

  let config = {
    title: 'Addresses',
    addButton: {
      url: '/inventory/addresses/add',
    },
    editButton: {
      disabled: false,
    },
    deleteButton: {
      disabled: false,
    },
    delete: {
      endpoint: '/addresses',
      keys: ['inventory', 'addresses'],
    },
    service: Services.INVENTORY,
  }
</script>

<FeatureMenuBar {config} />
<div id="address" class="flex flex-col gap-3 px-10 pt-10">
  <Pageable endpoint="/inventory/addresses" component={AddressTable} />
</div>
