<script lang="ts">
  import { onDestroy, onMount } from 'svelte'
  import { genericStore } from '$lib/stores/genericStore.ts'

  export let data: any = undefined
  let selectedCategories: Map<string, string> = new Map()

  onMount(() => {
    if (
      $genericStore.finances !== undefined &&
      $genericStore.finances.categories !== undefined
    ) {
      $genericStore.finances.categories.forEach((catg) =>
        selectedCategories.set(catg.uuid, catg)
      )
    }
  })

  onDestroy(() => {
    $genericStore.finances = {}
    $genericStore.finances.categories = Array.from(selectedCategories.values())
  })

  const updateCategoriesList = (category: string) => {
    if (selectedCategories.has(category.uuid)) {
      selectedCategories.delete(category.uuid)
    } else {
      selectedCategories.set(category.uuid, category)
    }
    selectedCategories = selectedCategories
  }

  const categorySelectedStyles = (
    categoryMap: Map<string, string>,
    categoryId: string
  ): string => {
    if (!categoryMap.has(categoryId)) return ''
    return 'bg-indigo-600 text-white'
  }

  const determineEvenBgColor = (
    categoryMap: Map<string, string>,
    categoryId: string
  ): string => {
    if (!categoryMap.has(categoryId)) return 'even:bg-black'
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
        <th>Operation Name</th>
        <th>Operation Type</th>
      </tr>
    </thead>
    <tbody>
      {#if data !== undefined}
        {#each data.content as operationCategory}
          <tr
            class={`hover:bg-indigo-400 hover:text-black even:text-white hover:even:text-black hover:even:bg-indigo-400 cursor-pointer ease-in transition-all duration-200
        ${categorySelectedStyles(selectedCategories, operationCategory.uuid)}
        ${determineEvenBgColor(selectedCategories, operationCategory.uuid)}`}
            on:click={() => updateCategoriesList(operationCategory)}
          >
            <td>{operationCategory.uuid}</td>
            <td>{operationCategory.operationName}</td>
            <td>{operationCategory.operationType}</td>
          </tr>
        {/each}
      {/if}
    </tbody>
  </table>
</div>
