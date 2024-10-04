<script lang="ts">
  import PartsTable from '$lib/inventory/parts/PartsTable.svelte'
  import Pageable from '$lib/Pageable.svelte'
  import FeatureMenuBar from '$lib/FeatureMenuBar.svelte'

  let config = {
    title: 'Parts',
    addButton: {
      url: '/inventory/parts/add',
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
<div id="part" class="flex flex-col gap-3 px-10 pt-10">
  <Pageable endpoint="/inventory/parts" component={PartsTable} />
</div>
