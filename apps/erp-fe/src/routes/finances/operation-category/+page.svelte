<script lang="ts">
  import Pageable from '$lib/Pageable.svelte'
  import OperationCategoryTable from '$lib/finances/operation-category/OperationCategoryTable.svelte'
  import FeatureMenuBar from '$lib/FeatureMenuBar.svelte'

  let config = {
    title: 'Operations Category',
    addButton: {
      url: '/finances/operation-category/add',
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
<div id="operation-category" class="flex flex-col gap-3 px-10 pt-10">
  <Pageable
    endpoint="/finances/operation-category"
    component={OperationCategoryTable}
  />
</div>
