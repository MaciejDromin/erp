<script lang="ts">
  import { onDestroy, onMount } from 'svelte'
  import { genericStore } from '$lib/stores/genericStore.ts'

  export let data: any = undefined
  let selectedItems: Map<string, string> = new Map()

  onMount(() => {
    if (
      $genericStore.inventory !== undefined &&
      $genericStore.inventory.items !== undefined
    ) {
      $genericStore.inventory.items.forEach((item) =>
        selectedItems.set(item.id, item)
      )
    }
  })

  onDestroy(() => {
    $genericStore.inventory = {}
    $genericStore.inventory.items = Array.from(selectedItems.values())
  })

  const convertCategories = (categories: any[]): string => {
    let categoryNames = categories.map((category: any) => category['name'])
    return categoryNames.toString()
  }

  const updateCategoriesList = (item: any) => {
    if (selectedItems.has(item.id)) {
      selectedItems.delete(item.id)
    } else {
      selectedItems.set(item.id, item)
    }
    selectedItems = selectedItems
  }

  const itemSelectedStyles = (
    itemMap: Map<string, string>,
    itemId: string
  ): string => {
    if (!itemMap.has(itemId)) return ''
    return 'bg-indigo-600 text-white'
  }

  const determineEvenBgColor = (
    itemMap: Map<string, string>,
    itemId: string
  ): string => {
    if (!itemMap.has(itemId)) return 'even:bg-black'
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
        <th>Quantity</th>
        <th>Unit</th>
        <th>Categories</th>
      </tr>
    </thead>
    <tbody>
      {#if data !== undefined}
        {#each data.content as item}
          <tr
            class={`hover:bg-indigo-400 hover:text-black even:text-white hover:even:text-black hover:even:bg-indigo-400 cursor-pointer ease-in transition-all duration-200
                ${itemSelectedStyles(selectedItems, item.id)}
                ${determineEvenBgColor(selectedItems, item.id)}`}
            on:click={() => updateCategoriesList(item)}
          >
            <td>{item.id}</td>
            <td>{item.name}</td>
            <td>{item.quantity}</td>
            <td>{item.unit}</td>
            <td>{convertCategories(item.categories)}</td>
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
