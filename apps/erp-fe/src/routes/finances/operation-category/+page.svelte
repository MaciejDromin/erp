<script lang="ts">
  import Pageable from '$lib/Pageable.svelte'
  import OperationCategoryTable from '$lib/finances/operation-category/OperationCategoryTable.svelte'
  import FeatureMenuBar from '$lib/FeatureMenuBar.svelte'
  import { Services } from '$lib/types/services.ts'
  import { genericStore } from '$lib/stores/genericStore.ts'

  let config = {
    title: 'Operations Category',
    addButton: {
      url: '/finances/operation-category/add',
    },
    editButton: {
      url: '/finances/operation-category/edit',
      disabled: true,
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

  const updateConfig = () => {
    if ($genericStore.finances === undefined) return

    if (
      $genericStore.finances.categories !== undefined &&
      $genericStore.finances.categories.length > 0
    ) {
      config.editButton.url = `/finances/operation-category/${$genericStore.finances.categories[0].uuid}/edit`
      config.editButton.disabled = false
      config = config
    } else {
      config.editButton.disabled = true
    }
  }

  $: $genericStore, updateConfig()
</script>

<FeatureMenuBar {config} />
<div id="operation-category" class="flex flex-col gap-3 px-10 pt-10">
  <Pageable
    endpoint="/finances/operation-category"
    component={OperationCategoryTable}
  />
</div>
