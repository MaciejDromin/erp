<script lang="ts">
  import { onDestroy, onMount } from 'svelte'
  import { genericStore } from '$lib/stores/genericStore.ts'

  export let data: any = undefined
  let selectedProperties: Map<string, string> = new Map()

  onMount(() => {
    if (
      $genericStore.inventory !== undefined &&
      $genericStore.inventory.properties !== undefined
    ) {
      $genericStore.inventory.properties.forEach((prop) =>
        selectedProperties.set(prop.id, prop)
      )
    }
  })

  onDestroy(() => {
    $genericStore.inventory = {}
    $genericStore.inventory.properties = Array.from(selectedProperties.values())
  })

  const updatePropertiesList = (property: any) => {
    if (selectedProperties.has(property.id)) {
      selectedProperties.delete(property.id)
    } else {
      selectedProperties.set(property.id, property)
    }
    selectedProperties = selectedProperties
    $genericStore.inventory = {}
    $genericStore.inventory.properties = Array.from(selectedProperties.values())
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
                ${propertySelectedStyles(selectedProperties, property.id)}
                ${determineEvenBgColor(selectedProperties, property.id)}`}
            on:click={() => updatePropertiesList(property)}
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
