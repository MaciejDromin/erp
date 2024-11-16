<script lang="ts">
  import Pageable from '$lib/Pageable.svelte'
  import MoneyOpearationTable from '$lib/finances/money-operation/MoneyOperationTable.svelte'
  import FeatureMenuBar from '$lib/FeatureMenuBar.svelte'
  import { Services } from '$lib/types/services.ts'
  import { genericStore } from '$lib/stores/genericStore.ts'

  let config = {
    title: 'Money Operations',
    addButton: {
      url: '/finances/money-operation/add',
    },
    editButton: {
      url: '/finances/money-operation/edit',
      disabled: true,
    },
    deleteButton: {
      disabled: false,
    },
    delete: {
      endpoint: '/finances/money-operation',
      keys: ['finances', 'moneyOperations'],
    },
    service: Services.FINANCES,
  }

  const updateConfig = () => {
    if ($genericStore.finances === undefined) return

    if (
      $genericStore.finances.moneyOperations !== undefined &&
      $genericStore.finances.moneyOperations.length > 0
    ) {
      config.editButton.url = `/finances/money-operation/${$genericStore.finances.moneyOperations[0].uuid}/edit`
      config.editButton.disabled = false
      config = config
    } else {
      config.editButton.disabled = true
    }
  }

  $: $genericStore, updateConfig()
</script>

<FeatureMenuBar {config} />
<div id="money-operation" class="flex flex-col gap-3 px-10 pt-10">
  <Pageable
    endpoint="/finances/money-operation"
    component={MoneyOpearationTable}
  />
</div>
