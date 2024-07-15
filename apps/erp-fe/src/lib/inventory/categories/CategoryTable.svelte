<script lang="ts">
  import { onDestroy, onMount } from 'svelte'
  import { categoriesStore } from '../stores/selectedCategories'

  export let data: any = undefined
  let selectedCategoriesIds: Map<string, string> = new Map()

  onMount(() => {
    $categoriesStore.forEach((cat) => selectedCategoriesIds.set(cat, 'ok'))
  })

  onDestroy(() => {
    $categoriesStore = Array.from(selectedCategoriesIds.keys())
  })

  const updateCategoriesList = (categoryId: string) => {
    if (selectedCategoriesIds.has(categoryId)) {
      selectedCategoriesIds.delete(categoryId)
    } else {
      selectedCategoriesIds.set(categoryId, 'ok')
    }
    selectedCategoriesIds = selectedCategoriesIds
  }

  const categorySelectedStyles = (
    categoriesMap: Map<string, string>,
    categoryId: string
  ): string => {
    if (!categoriesMap.has(categoryId)) return ''
    return 'bg-indigo-600 text-white'
  }

  const determineEvenBgColor = (
    categoriesMap: Map<string, string>,
    categoryId: string
  ): string => {
    if (!categoriesMap.has(categoryId)) return 'even:bg-black'
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
        <th>Name</th>
      </tr>
    </thead>
    <tbody>
      {#if data !== undefined}
        {#each data.content as category}
          <tr
            class={`hover:bg-indigo-400 hover:text-black even:text-white hover:even:text-black hover:even:bg-indigo-400 cursor-pointer ease-in transition-all duration-200
                        ${categorySelectedStyles(
                          selectedCategoriesIds,
                          category.id
                        )}
                        ${determineEvenBgColor(
                          selectedCategoriesIds,
                          category.id
                        )}`}
            on:click={() => updateCategoriesList(category.id)}
          >
            <td>{category.id}</td>
            <td>{category.name}</td>
          </tr>
        {/each}
      {/if}
    </tbody>
  </table>
</div>
<!-- <style>
    .bg-indigo-600-important {
        --tw-bg-opacity: 1;
        background-color: rgb(79 70 229 / var(--tw-bg-opacity)) !important;
    }
</style> -->
