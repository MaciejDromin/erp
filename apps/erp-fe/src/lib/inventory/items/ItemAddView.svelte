<script lang="ts">
  import CategoryTable from '$lib/inventory/categories/CategoryTable.svelte'
  import Modal from '$lib/Modal.svelte'
  import Pageable from '$lib/Pageable.svelte'
  import { genericStore } from '$lib/stores/genericStore.ts'
  import { onMount } from 'svelte'
  import { ItemUnit } from '$lib/inventory/types/inventoryTypes'

  let categories: any[] = []
  let selectedCategory

  onMount(() => {
    $genericStore = {}
  })

  genericStore.subscribe((catgz) => {
    if (
      catgz.inventory !== undefined &&
      catgz.inventory.categories !== undefined &&
      typeof catgz.inventory.categories[Symbol.iterator] === 'function'
    ) {
      categories = [...catgz.inventory.categories]
      categories = categories
      selectedCategory = JSON.stringify(categories[0])
    }
  })

  const determineButtonName = (arr: any[]): string => {
    if (arr.length === 0) return 'select categories'
    return `${arr.length} categories selected`
  }
</script>

<form method="POST" class="mx-auto flex flex-col gap-3 py-6">
  <div class="flex flex-row gap-3">
    <input
      name="name"
      type="text"
      placeholder="Name"
      class="input input-bordered input-primary w-full max-w-xs"
    />
    <input
      name="quantity"
      type="text"
      placeholder="Quantity"
      class="input input-bordered input-primary w-full max-w-xs"
    />
  </div>

  <div class="flex flex-row gap-3">
    <select
      multiple
      name="category"
      class="p-4 mr-auto hidden"
      bind:value={selectedCategory}
    >
      {#each categories as category}
        <option value={JSON.stringify(category)}></option>
      {/each}
    </select>
    <select name="unit" class="select select-primary w-full max-w-xs">
      {#each Object.values(ItemUnit) as unit}
        <option value={unit}>{unit}</option>
      {/each}
    </select>
    <div class="mr-auto">
      <Modal
        modalId="category_modal"
        buttonName={determineButtonName(categories)}
      >
        <Pageable endpoint="/inventory/categories" component={CategoryTable} />
      </Modal>
    </div>
    <button class="btn btn-primary">Add Row</button>
  </div>
</form>
