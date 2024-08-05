<script lang="ts">
  import { onDestroy, onMount } from 'svelte'
  import { partsStore } from '../stores/selectedParts'

  export let data: any = undefined
  let selectedPartsIds: Map<string, string> = new Map()

  onMount(() => {
    $partsStore.forEach((part) =>
      selectedPartsIds.set(JSON.parse(part).id, part)
    )
  })

  onDestroy(() => {
    $partsStore = Array.from(selectedPartsIds.values())
  })

  const updatePartsList = (partId: string, name: string) => {
    const partObj = JSON.stringify({ id: partId, name: name })
    if (selectedPartsIds.has(partId)) {
      selectedPartsIds.delete(partId)
    } else {
      selectedPartsIds.set(partId, partObj)
    }
    selectedPartsIds = selectedPartsIds
  }

  const partSelectedStyles = (
    partMap: Map<string, string>,
    partId: string
  ): string => {
    if (!partMap.has(partId)) return ''
    return 'bg-indigo-600 text-white'
  }

  const determineEvenBgColor = (
    partMap: Map<string, string>,
    partId: string
  ): string => {
    if (!partMap.has(partId)) return 'even:bg-black'
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
        <th>Part Number</th>
        <th>Manufacturer ID</th>
      </tr>
    </thead>
    <tbody>
      {#if data !== undefined}
        {#each data.content as part}
          <tr
            class={`hover:bg-indigo-400 hover:text-black even:text-white hover:even:text-black hover:even:bg-indigo-400 cursor-pointer ease-in transition-all duration-200
                ${partSelectedStyles(selectedPartsIds, part.id)}
                ${determineEvenBgColor(selectedPartsIds, part.id)}`}
            on:click={() => updatePartsList(part.id, part.name)}
          >
            <td>{part.id}</td>
            <td>{part.name}</td>
            <td>{part.partNumber}</td>
            <td>{part.manufacturerId}</td>
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
