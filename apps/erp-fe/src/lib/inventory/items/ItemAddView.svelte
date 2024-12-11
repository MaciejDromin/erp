<script lang="ts">
  import CategoryTable from '$lib/inventory/categories/CategoryTable.svelte'
  import Modal from '$lib/Modal.svelte'
  import Pageable from '$lib/Pageable.svelte'
  import { genericStore } from '$lib/stores/genericStore.ts'
  import { onMount } from 'svelte'
  import { ItemUnit } from '$lib/inventory/types/inventoryTypes'
  import TextInput from '$lib/commons/TextInput.svelte'
  import SelectInput from '$lib/commons/SelectInput.svelte'
  import InputSection from '$lib/commons/InputSection.svelte'

  export let data = undefined

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

  let itemId = data === undefined ? '' : data.item.id
  let name = data === undefined ? '' : data.item.name
  let quantity = data === undefined ? '' : data.item.quantity
  let unit = data === undefined ? '' : data.item.unit
  let buttonName = data === undefined ? 'Add' : 'Edit'
</script>

<form method="POST" class="mx-auto flex flex-col gap-3 justify-center">
  <input name="itemId" type="text" class="hidden" bind:value={itemId} />
  <div class="flex flex-row gap-3 w-fit mx-auto">
    <div>
      <InputSection name="Item" classes=" flex-row gap-2 items-center">
        <TextInput
          name="name"
          bind:value={name}
          placeholder="Name"
          classes=" bg-white text-black"
        />
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
        <div class="mr-auto">
          <Modal
            modalId="category_modal"
            buttonName={determineButtonName(categories)}
          >
            <Pageable endpoint="/inventory/categories" component={CategoryTable} />
            <button slot="button" class="btn btn-primary"
              >{determineButtonName(categories)}</button
            >
          </Modal>
        </div>
      </InputSection>
    </div>
    <div>
      <InputSection name="Quantity" classes=" flex-row gap-2 min-h-full items-center">
        <TextInput
          name="name"
          bind:value={name}
          placeholder="Name"
          classes=" bg-white text-black"
        />
        <SelectInput
          name="unit"
          bind:value={unit}
          classes=" bg-white text-black"
          options={Object.values(ItemUnit)}
        />
      </InputSection>
    </div>
  </div>
  <button class="btn btn-primary mx-auto">{buttonName}</button>
</form>
