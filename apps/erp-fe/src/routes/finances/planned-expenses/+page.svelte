<script lang="ts">
  import Pageable from '$lib/Pageable.svelte'
  import PlannedExpensesTable from '$lib/finances/planned-expenses/PlannedExpensesTable.svelte'
  import FeatureMenuBar from '$lib/FeatureMenuBar.svelte'
  import { Services } from '$lib/types/services.ts'

  let config = {
    title: 'Planned Expenses',
    addButton: {
      url: '/finances/planned-expenses/add',
    },
    editButton: {
      disabled: true,
    },
    deleteButton: {
      disabled: false,
    },
    delete: {
      endpoint: '/finances/planned-expenses',
      keys: ['finances', 'plannedexpenses'],
    },
    service: Services.FINANCES,
  }
</script>

<FeatureMenuBar {config} />
<div id="planned-expenses" class="flex flex-col gap-3 px-10 pt-10">
  <Pageable
    endpoint="/finances/planned-expenses"
    component={PlannedExpensesTable}
  />
</div>
