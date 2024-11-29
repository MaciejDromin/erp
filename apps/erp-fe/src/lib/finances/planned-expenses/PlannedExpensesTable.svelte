<script lang="ts">
  import { onDestroy, onMount } from 'svelte'
  import { genericStore } from '$lib/stores/genericStore.ts'

  export let data: any = undefined

  let actualAmountByIdMap: { [key: string]: string } = {}

  let selectedPlannedExpenses: Map<string, string> = new Map()

  onMount(() => {
    if (
      $genericStore.finances !== undefined &&
      $genericStore.finances.plannedexpenses !== undefined
    ) {
      $genericStore.finances.plannedexpenses.forEach((catg) =>
        selectedPlannedExpenses.set(catg.id, catg)
      )
    }
  })

  onDestroy(() => {
    $genericStore.finances = {}
    $genericStore.finances.plannedexpenses = Array.from(
      selectedPlannedExpenses.values()
    )
  })

  const updatePlannedExpensesList = (plannedExpense: string) => {
    if (selectedPlannedExpenses.has(plannedExpense.id)) {
      selectedPlannedExpenses.delete(plannedExpense.id)
    } else {
      selectedPlannedExpenses.set(plannedExpense.id, plannedExpense)
    }
    selectedPlannedExpenses = selectedPlannedExpenses
    $genericStore.finances = {}
    $genericStore.finances.plannedexpenses = Array.from(
      selectedPlannedExpenses.values()
    )
  }

  const plannedExpenseSelectedStyles = (
    plannedExpenseMap: Map<string, string>,
    plannedExpenseId: string
  ): string => {
    if (!plannedExpenseMap.has(plannedExpenseId)) return ''
    return 'bg-indigo-600 text-white'
  }

  const determineEvenBgColor = (
    plannedExpenseMap: Map<string, string>,
    plannedExpenseId: string
  ): string => {
    if (!plannedExpenseMap.has(plannedExpenseId)) return 'even:bg-black'
    return 'even:bg-indigo-600'
  }

  const updateAmountStore = () => {
    if ($genericStore.finances === undefined) {
      $genericStore.finances = {}
    }
    $genericStore.finances.actual = actualAmountByIdMap
  }

  $: actualAmountByIdMap, updateAmountStore()
</script>

<div class="overflow-x-auto text-primary-content mx-auto">
  <table class="table">
    <thead class="text-primary-content">
      <tr>
        <th>ID</th>
        <th>Operation Category</th>
        <th>Operation Description</th>
        <th>Planned Amount</th>
        <th>Actual Amount</th>
        <th>Currency</th>
        <th>Planned Year</th>
        <th>Planned Month</th>
        <th>Status</th>
        <th>Finalized Date</th>
      </tr>
    </thead>
    <tbody>
      {#if data !== undefined}
        {#each data.content as plannedExpenses}
          <tr
            class={`hover:bg-indigo-400 hover:text-black even:text-white hover:even:text-black hover:even:bg-indigo-400 cursor-pointer ease-in transition-all duration-200
        ${plannedExpenseSelectedStyles(selectedPlannedExpenses, plannedExpenses.id)}
        ${determineEvenBgColor(selectedPlannedExpenses, plannedExpenses.id)}`}
            on:click={() => updatePlannedExpensesList(plannedExpenses)}
          >
            <td>{plannedExpenses.id}</td>
            <td>{plannedExpenses.operationCategory.operationName}</td>
            <td>{plannedExpenses.operationDescription}</td>
            <td>{plannedExpenses.plannedAmount.value}</td>
            <td>
              {#if plannedExpenses.plannedExpensesStatus === 'PLANNED'}
                <input
                  name="amountHolder"
                  class="input input-bordered input-primary w-full max-w-xs text-white"
                  bind:value={actualAmountByIdMap[plannedExpenses.id]}
                  type="text"
                />
              {:else}
                {plannedExpenses.actualAmount.value}
              {/if}
            </td>
            <td>{plannedExpenses.plannedAmount.currencyCode}</td>
            <td>{plannedExpenses.plannedYear}</td>
            <td>{plannedExpenses.plannedMonth}</td>
            <td>{plannedExpenses.plannedExpensesStatus}</td>
            <td
              >{plannedExpenses.finalizedDate === undefined
                ? 'No Value'
                : plannedExpenses.finalizedDate}</td
            >
          </tr>
        {/each}
      {/if}
    </tbody>
  </table>
</div>
