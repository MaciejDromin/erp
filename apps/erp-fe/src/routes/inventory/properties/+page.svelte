<script lang="ts">
  import PropertyTable from '$lib/inventory/properties/PropertyTable.svelte'
  import Pageable from '$lib/Pageable.svelte'
  import FeatureMenuBar from '$lib/FeatureMenuBar.svelte'

  let config = {
    title: 'Properties',
    addButton: {
      url: '/inventory/properties/add',
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
<div id="property" class="flex flex-col gap-3 px-10 pt-10">
  <Pageable endpoint="/inventory/properties" component={PropertyTable} />
</div>
