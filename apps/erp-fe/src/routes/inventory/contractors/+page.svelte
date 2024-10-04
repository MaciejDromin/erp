<script lang="ts">
  import ContractorsTable from '$lib/inventory/contractors/ContractorsTable.svelte'
  import Pageable from '$lib/Pageable.svelte'
  import FeatureMenuBar from '$lib/FeatureMenuBar.svelte'

  let config = {
    title: 'Contractors',
    addButton: {
      url: '/inventory/contractors/add',
    },
    editButton: {
      disabled: false,
    },
    deleteButton: {
      disabled: false,
    },
  }
</script>

<FeatureMenuBar {config} />
<div id="contractor" class="flex flex-col gap-3 px-10 pt-10">
  <Pageable endpoint="/inventory/contractors" component={ContractorsTable} />
</div>
