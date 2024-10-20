<script lang="ts">
  import Pageable from '$lib/Pageable.svelte'
  import OperationCategoryTable from '$lib/finances/operation-category/OperationCategoryTable.svelte'
  import FeatureMenuBar from '$lib/FeatureMenuBar.svelte'
  import { Services } from '$lib/types/services.ts'

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
    delete: {
      endpoint: '/finances/operation-category',
      keys: ['finances', 'categories'],
    },
    service: Services.FINANCES,
  }
</script>

<FeatureMenuBar {config} />
<div id="operation-category" class="flex flex-col gap-3 px-10 pt-10">
  <Pageable
    endpoint="/finances/operation-category"
    component={OperationCategoryTable}
  />
</div>
