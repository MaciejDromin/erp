<script lang="ts">
  import { onDestroy, onMount } from 'svelte'
  import { genericStore } from '$lib/stores/genericStore.ts'

  export let data: any = undefined
  let selectedPeriodicals: Map<string, string> = new Map()

  onMount(() => {
    if (
      $genericStore.finances !== undefined &&
      $genericStore.finances.periodicals !== undefined
    ) {
      $genericStore.finances.periodicals.forEach((catg) =>
        selectedPeriodicals.set(catg.uuid, catg)
      )
    }
  })

  onDestroy(() => {
    $genericStore.finances = {}
    $genericStore.finances.periodicals = Array.from(
      selectedPeriodicals.values()
    )
  })

  const updatePeriodicalsList = (periodical: string) => {
    if (selectedPeriodicals.has(periodical.uuid)) {
      selectedPeriodicals.delete(periodical.uuid)
    } else {
      selectedPeriodicals.set(periodical.uuid, periodical)
    }
    selectedPeriodicals = selectedPeriodicals
    $genericStore.finances = {}
    $genericStore.finances.periodicals = Array.from(
      selectedPeriodicals.values()
    )
  }

  const periodicalSelectedStyles = (
    periodicalMap: Map<string, string>,
    periodicalId: string
  ): string => {
    if (!periodicalMap.has(periodicalId)) return ''
    return 'bg-indigo-600 text-white'
  }

  const determineEvenBgColor = (
    periodicalMap: Map<string, string>,
    periodicalId: string
  ): string => {
    if (!periodicalMap.has(periodicalId)) return 'even:bg-black'
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
        <th>Next Applicable Month</th>
      </tr>
    </thead>
    <tbody>
      {#if data !== undefined}
        {#each data.content as periodical}
          <tr
            class={`hover:bg-indigo-400 hover:text-black even:text-white hover:even:text-black hover:even:bg-indigo-400 cursor-pointer ease-in transition-all duration-200
        ${periodicalSelectedStyles(selectedPeriodicals, periodical.uuid)}
        ${determineEvenBgColor(selectedPeriodicals, periodical.uuid)}`}
            on:click={() => updatePeriodicalsList(periodical)}
          >
            <td>{periodical.uuid}</td>
            <td>{periodical.operationCategory.operationType}</td>
            <td>{periodical.operationCategory.operationName}</td>
            <td>{periodical.operationDescription}</td>
            <td>{periodical.amount.value}</td>
            <td>{periodical.amount.currencyCode}</td>
            <td>{periodical.nextApplicableMonth}</td>
          </tr>
        {/each}
      {/if}
    </tbody>
  </table>
</div>
