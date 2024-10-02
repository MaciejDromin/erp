<script lang="ts">
  import ItemTable from '$lib/inventory/items/ItemTable.svelte'
  import Pageable from '$lib/Pageable.svelte'
  import FeatureMenuBar from '$lib/FeatureMenuBar.svelte'

  let config = {
    title: 'Items',
    addButton: {
      url: '/inventory/items/add',
    },
    editButton: {
      disabled: false,
    },
    deleteButton: {
      disabled: false,
    },
  }
</script>

<div id="item" class="flex flex-col gap-3 px-10 pt-10">
  <FeatureMenuBar {config} />

  <Pageable endpoint="/inventory/items" component={ItemTable} />
</div>
