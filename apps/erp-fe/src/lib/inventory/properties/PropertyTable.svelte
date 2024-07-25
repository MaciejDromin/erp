<script lang="ts">
  import { onDestroy, onMount } from 'svelte'
  import { propertiesStore } from '../stores/selectedProperties'

  export let data: any = undefined
  let selectedPropertiesIds: Map<string, string> = new Map()

  onMount(() => {
    $propertiesStore.forEach((prop) => selectedPropertiesIds.set(prop, 'ok'))
  })

  onDestroy(() => {
    $propertiesStore = Array.from(selectedPropertiesIds.keys())
  })

  const updatePropertiesList = (propertyId: string) => {
    if (selectedPropertiesIds.has(propertyId)) {
      selectedPropertiesIds.delete(propertyId)
    } else {
      selectedPropertiesIds.set(propertyId, 'ok')
    }
    selectedPropertiesIds = selectedPropertiesIds
  }

  const propertySelectedStyles = (
    propertyMap: Map<string, string>,
    propertyId: string
  ): string => {
    if (!propertyMap.has(propertyId)) return ''
    return 'bg-indigo-600 text-white'
  }

  const determineEvenBgColor = (
    propertyMap: Map<string, string>,
    propertyId: string
  ): string => {
    if (!propertyMap.has(propertyId)) return 'even:bg-black'
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
        <th>Unique Identifier</th>
        <th>Land Register</th>
        <th>Property Type</th>
      </tr>
    </thead>
    <tbody>
      {#if data !== undefined}
        {#each data.content as property}
          <tr
            class={`hover:bg-indigo-400 hover:text-black even:text-white hover:even:text-black hover:even:bg-indigo-400 cursor-pointer ease-in transition-all duration-200
                ${propertySelectedStyles(selectedPropertiesIds, property.id)}
                ${determineEvenBgColor(selectedPropertiesIds, property.id)}`}
            on:click={() => updatePropertiesList(property.id)}
          >
            <td>{property.id}</td>
            <td>{property.name}</td>
            <td>{property.uniqueIdentifier}</td>
            <td>{property.landRegister}</td>
            <td>{property.propertyType}</td>
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
