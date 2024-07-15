<script lang="ts">
  import { onDestroy, onMount } from 'svelte'
  import { operationCategoriesStore } from '../stores/selectedOperationCategory'

  export let data: any = undefined
  let selectedCategoryIds: Map<string, string> = new Map()

  onMount(() => {
    $operationCategoriesStore.forEach((cat) =>
      selectedCategoryIds.set(cat, 'ok')
    )
  })

  onDestroy(() => {
    $operationCategoriesStore = Array.from(selectedCategoryIds.keys())
  })

  const updateCategoriesList = (categoryId: string) => {
    if (selectedCategoryIds.has(categoryId)) {
      selectedCategoryIds.delete(categoryId)
    } else {
      selectedCategoryIds.set(categoryId, 'ok')
    }
    selectedCategoryIds = selectedCategoryIds
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
        ${categorySelectedStyles(selectedCategoryIds, operationCategory.uuid)}
        ${determineEvenBgColor(selectedCategoryIds, operationCategory.uuid)}`}
            on:click={() => updateCategoriesList(operationCategory.uuid)}
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
