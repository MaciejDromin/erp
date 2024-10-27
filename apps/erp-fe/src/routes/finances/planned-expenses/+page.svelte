<script lang="ts">
  import Pageable from '$lib/Pageable.svelte'
  import PlannedExpensesTable from '$lib/finances/planned-expenses/PlannedExpensesTable.svelte'
  import FeatureMenuBar from '$lib/FeatureMenuBar.svelte'
  import { Services } from '$lib/types/services.ts'
  import { genericStore } from '$lib/stores/genericStore.ts'

  let actualMap = {}
  let actualArray = []

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

  const updateActual = () => {
    if ($genericStore.finances === undefined) return
    if ($genericStore.finances.actual !== undefined)
      actualMap = $genericStore.finances.actual

    if ($genericStore.finances.plannedexpenses !== undefined)
      actualArray = $genericStore.finances.plannedexpenses
  }

  $: $genericStore, updateActual()
</script>

<FeatureMenuBar {config}>
  <div class="flex flex-row">
    <form method="POST" action="?/complete" class="mr-3">
      <input
        name="plannedExpensesMap"
        class="hidden"
        value={JSON.stringify(actualMap)}
        type="text"
      />
      <button class="btn btn-primary mx-auto">Complete</button>
    </form>
    <form method="POST" action="?/abandon" class="mr-3">
      <input
        name="plannedExpensesArr"
        class="hidden"
        value={JSON.stringify(actualArray)}
        type="text"
      />
      <button class="btn btn-primary mx-auto">Abandon</button>
    </form>
  </div>
</FeatureMenuBar>
<div id="planned-expenses" class="flex flex-col gap-3 px-10 pt-10">
  <Pageable
    endpoint="/finances/planned-expenses"
    component={PlannedExpensesTable}
  />
</div>
