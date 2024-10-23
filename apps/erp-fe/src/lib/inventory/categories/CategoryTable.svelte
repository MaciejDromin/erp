<script lang="ts">
  import { onDestroy, onMount } from 'svelte'
  import { genericStore } from '$lib/stores/genericStore.ts'

  export let data: any = undefined
  let selectedCategories: Map<string, string> = new Map()

  onMount(() => {
    if (
      $genericStore.inventory !== undefined &&
      $genericStore.inventory.categories !== undefined
    ) {
      $genericStore.inventory.categories.forEach((catg) =>
        selectedCategories.set(catg.id, catg)
      )
    }
  })

  onDestroy(() => {
    $genericStore.inventory = {}
    $genericStore.inventory.categories = Array.from(selectedCategories.values())
  })

  const updateCategoriesList = (category: any) => {
    if (selectedCategories.has(category.id)) {
      selectedCategories.delete(category.id)
    } else {
      selectedCategories.set(category.id, category)
    }
    selectedCategories = selectedCategories
    $genericStore.inventory = {}
    $genericStore.inventory.categories = Array.from(selectedCategories.values())
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
                          selectedCategories,
                          category.id
                        )}
                        ${determineEvenBgColor(
                          selectedCategories,
                          category.id
                        )}`}
            on:click={() => updateCategoriesList(category)}
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
