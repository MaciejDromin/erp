<script lang="ts">
  import { onDestroy } from 'svelte'
  import { selectedMaterials } from '../stores/selectedMaterials'

  export let data: any = undefined
  export let materialPos: any = undefined
  export let phasePos: any = undefined
  let selectedMaterialId: string = ''

  onDestroy(() => {
    $selectedMaterials = {
      selectedMaterialId: selectedMaterialId,
      materialPos: materialPos,
      phasePos: phasePos,
    }
  })

  const updateSelectedMaterial = (materialId: string) => {
    if (selectedMaterialId === materialId) selectedMaterialId = ''
    else selectedMaterialId = materialId
    selectedMaterialId = selectedMaterialId
  }

  const selectedMaterialStyle = (
    materialId: string,
    selectedMaterialId: string
  ): string => {
    if (selectedMaterialId !== materialId) return ''
    return 'bg-indigo-600 text-white'
  }

  const determineEvenBgColor = (
    materialId: string,
    selectedMaterialId: string
  ): string => {
    if (selectedMaterialId !== materialId) return 'even:bg-black'
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
        <th>Unit</th>
        <th>Unit Amount</th>
        <th>Currency</th>
        <th>Source</th>
        <th>updatedTime</th>
      </tr>
    </thead>
    <tbody>
      {#if data !== undefined}
        {#each data.content as material}
          <tr
            class={`hover:bg-indigo-400 hover:text-black even:text-white hover:even:text-black hover:even:bg-indigo-400 cursor-pointer ease-in transition-all duration-200
                        ${selectedMaterialStyle(
                          material.id,
                          selectedMaterialId
                        )}
                        ${determineEvenBgColor(
                          material.id,
                          selectedMaterialId
                        )}`}
            on:click={() => updateSelectedMaterial(material.id)}
          >
            <td>{material.id}</td>
            <td>{material.name}</td>
            <td>{material.unit}</td>
            <td>{material.unitAmount.value}</td>
            <td>{material.unitAmount.currencyCode}</td>
            <td>{material.source}</td>
            <td>{material.updatedTime}</td>
          </tr>
        {/each}
      {/if}
    </tbody>
  </table>
</div>
