<script lang="ts">
  import { onDestroy, onMount } from 'svelte'
  import { contractorsStore } from '../stores/selectedContractors'

  export let data: any = undefined
  let selectedContractorsIds: Map<string, string> = new Map()

  onMount(() => {
    $contractorsStore.forEach((contr) => selectedContractorsIds.set(contr, 'ok'))
  })

  onDestroy(() => {
    $contractorsStore = Array.from(selectedContractorsIds.keys())
  })

  const updateContractorsList = (contractorId: string) => {
    if (selectedContractorsIds.has(contractorId)) {
      selectedContractorsIds.delete(contractorId)
    } else {
      selectedContractorsIds.set(contractorId, 'ok')
    }
    selectedContractorsIds = selectedContractorsIds
  }

  const contractorSelectedStyles = (
    contractorMap: Map<string, string>,
    contractorId: string
  ): string => {
    if (!contractorMap.has(contractorId)) return ''
    return 'bg-indigo-600 text-white'
  }

  const determineEvenBgColor = (
    contractorMap: Map<string, string>,
    contractorId: string
  ): string => {
    if (!contractorMap.has(contractorId)) return 'even:bg-black'
    return 'even:bg-indigo-600'
  }
</script>

<div class="overflow-x-auto text-primary-content mx-auto">
  <!-- <table class="table table-zebra"> -->
  <table class="table">
    <!-- head -->
    <thead class="text-primary-content">
      <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Phone Number</th>
        <th>Email</th>
        <th>Website</th>
      </tr>
    </thead>
    <tbody>
      {#if data !== undefined}
        {#each data.content as contractor}
          <tr
            class={`hover:bg-indigo-400 hover:text-black even:text-white hover:even:text-black hover:even:bg-indigo-400 cursor-pointer ease-in transition-all duration-200
                ${contractorSelectedStyles(selectedContractorsIds, contractor.id)}
                ${determineEvenBgColor(selectedContractorsIds, contractor.id)}`}
            on:click={() => updateContractorsList(contractor.id)}
          >
            <td>{contractor.id}</td>
            <td>{contractor.name}</td>
            <td>{contractor.contactInformation.phoneNumber}</td>
            <td>{contractor.contactInformation.email}</td>
            <td>{contractor.contactInformation.website}</td>
          </tr>
        {/each}
      {/if}
    </tbody>
  </table>
</div>

<!-- <style>
    .table-zebra tbody tr:nth-child(even) {
        color: white;
    }
</style> -->
