<script lang="ts">
  import { onDestroy, onMount } from 'svelte'
  import { genericStore } from '$lib/stores/genericStore.ts'

  export let data: any = undefined
  let selectedContractors: Map<string, string> = new Map()

  onMount(() => {
    if (
      $genericStore.inventory !== undefined &&
      $genericStore.inventory.contractors !== undefined
    ) {
      $genericStore.inventory.contractors.forEach((contr) =>
        selectedContractors.set(contr.id, contr)
      )
    }
  })

  onDestroy(() => {
    $genericStore.inventory = {}
    $genericStore.inventory.contractors = Array.from(selectedContractors.values())
  })

  const updateContractorsList = (contractor: any) => {
    if (selectedContractors.has(contractor.id)) {
      selectedContractors.delete(contractor.id)
    } else {
      selectedContractors.set(contractor.id, contractor)
    }
    selectedContractors = selectedContractors
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
                ${contractorSelectedStyles(selectedContractors, contractor.id)}
                ${determineEvenBgColor(selectedContractors, contractor.id)}`}
            on:click={() => updateContractorsList(contractor)}
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
