<script lang="ts">
  import Pageable from '$lib/Pageable.svelte'
  import PeriodicalMoneyOperationTable from '$lib/finances/money-operation/periodical/PeriodicalMoneyOperationTable.svelte'
  import FeatureMenuBar from '$lib/FeatureMenuBar.svelte'
  import { Services } from '$lib/types/services.ts'

  let config = {
    title: 'Periodical Money Operations',
    addButton: {
      url: '/finances/money-operation/periodical/add',
    },
    editButton: {
      disabled: false,
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
</script>

<FeatureMenuBar {config} />
<div id="periodical-money-operation" class="flex flex-col gap-3 px-10 pt-10">
  <Pageable
    endpoint="/finances/money-operation/periodical"
    component={PeriodicalMoneyOperationTable}
  />
</div>
