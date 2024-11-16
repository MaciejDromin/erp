<script lang="ts">
  import Pageable from '$lib/Pageable.svelte'
  import PeriodicalMoneyOperationTable from '$lib/finances/money-operation/periodical/PeriodicalMoneyOperationTable.svelte'
  import FeatureMenuBar from '$lib/FeatureMenuBar.svelte'
  import { Services } from '$lib/types/services.ts'
  import { genericStore } from '$lib/stores/genericStore.ts'

  let config = {
    title: 'Periodical Money Operations',
    addButton: {
      url: '/finances/money-operation/periodical/add',
    },
    editButton: {
      url: '/finances/money-operation/periodical/edit',
      disabled: true,
    },
    deleteButton: {
      disabled: false,
    },
    delete: {
      endpoint: '/finances/money-operation/periodical',
      keys: ['finances', 'periodicals'],
    },
    service: Services.FINANCES,
  }

  const updateConfig = () => {
    if ($genericStore.finances === undefined) return

    if (
      $genericStore.finances.periodicals !== undefined &&
      $genericStore.finances.periodicals.length > 0
    ) {
      config.editButton.url = `/finances/money-operation/periodical/${$genericStore.finances.periodicals[0].uuid}/edit`
      config.editButton.disabled = false
      config = config
    } else {
      config.editButton.disabled = true
    }
  }

  $: $genericStore, updateConfig()
</script>

<FeatureMenuBar {config} />
<div id="periodical-money-operation" class="flex flex-col gap-3 px-10 pt-10">
  <Pageable
    endpoint="/finances/money-operation/periodical"
    component={PeriodicalMoneyOperationTable}
  />
</div>
