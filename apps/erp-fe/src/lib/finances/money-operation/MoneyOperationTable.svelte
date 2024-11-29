<script lang="ts">
  import { onDestroy, onMount } from 'svelte'
  import { genericStore } from '$lib/stores/genericStore.ts'

  export let data: any = undefined
  let selectedMoneyOperations: Map<string, string> = new Map()

  onMount(() => {
    if (
      $genericStore.finances !== undefined &&
      $genericStore.finances.moneyOperations !== undefined
    ) {
      $genericStore.finances.moneyOperations.forEach((catg) =>
        selectedMoneyOperations.set(catg.id, catg)
      )
    }
  })

  onDestroy(() => {
    $genericStore.finances = {}
    $genericStore.finances.moneyOperations = Array.from(
      selectedMoneyOperations.values()
    )
  })

  const updateMoneyOperationsList = (moneyOperation: string) => {
    if (selectedMoneyOperations.has(moneyOperation.id)) {
      selectedMoneyOperations.delete(moneyOperation.id)
    } else {
      selectedMoneyOperations.set(moneyOperation.id, moneyOperation)
    }
    selectedMoneyOperations = selectedMoneyOperations
    $genericStore.finances = {}
    $genericStore.finances.moneyOperations = Array.from(
      selectedMoneyOperations.values()
    )
  }

  const moneyOperationSelectedStyles = (
    moneyOperationMap: Map<string, string>,
    moneyOperationId: string
  ): string => {
    if (!moneyOperationMap.has(moneyOperationId)) return ''
    return 'bg-indigo-600 text-white'
  }

  const determineEvenBgColor = (
    moneyOperationMap: Map<string, string>,
    moneyOperationId: string
  ): string => {
    if (!moneyOperationMap.has(moneyOperationId)) return 'even:bg-black'
    return 'even:bg-indigo-600'
  }
</script>

<div class="overflow-x-auto text-primary-content mx-auto">
  <!-- <table class="table table-zebra">  -->
  <table class="table">
    <!-- head -->
    <thead class="text-primary-content">
      <tr>
        <th>ID</th>
        <th>Operation Type</th>
        <th>Operation Category</th>
        <th>Operation Description</th>
        <th>Amount</th>
        <th>Currency</th>
        <th>Effective Date</th>
      </tr>
    </thead>
    <tbody>
      {#if data !== undefined}
        {#each data.content as moneyOperation}
          <tr
            class={`hover:bg-indigo-400 hover:text-black even:text-white hover:even:text-black hover:even:bg-indigo-400 cursor-pointer ease-in transition-all duration-200
        ${moneyOperationSelectedStyles(selectedMoneyOperations, moneyOperation.id)}
        ${determineEvenBgColor(selectedMoneyOperations, moneyOperation.id)}`}
            on:click={() => updateMoneyOperationsList(moneyOperation)}
          >
            <td>{moneyOperation.id}</td>
            <td>{moneyOperation.operationType}</td>
            <td
              >{moneyOperation.operationCategory === null
                ? 'EMPTY'
                : moneyOperation.operationCategory.operationName}</td
            >
            <td>{moneyOperation.operationDescription}</td>
            <td>{moneyOperation.amount.value}</td>
            <td>{moneyOperation.amount.currencyCode}</td>
            <td>{moneyOperation.effectiveDate}</td>
          </tr>
        {/each}
      {/if}
    </tbody>
  </table>
</div>
