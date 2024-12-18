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
  import { extractValue } from '$lib/scripts/dataExtractor.ts'
  import itemKeys from '$lib/inventory/types/itemKeys.ts'

  export let data = undefined
  export let form

  let itemId = extractValue(data, form, itemKeys.id)
  let name = extractValue(data, form, itemKeys.name)
  let quantity = extractValue(data, form, itemKeys.quantity)
  let unit = extractValue(data, form, itemKeys.unit)
  let categories: any[] = extractValue(data, form, itemKeys.category, [])
  let selectedCategory = JSON.stringify(categories[0])
  let buttonName = data === undefined ? 'Add' : 'Edit'

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

<form method="POST" class="mx-auto flex flex-col gap-3">
  <input name="itemId" type="text" class="hidden" bind:value={itemId} />
  <div class="flex flex-row gap-3 w-fit mx-auto">
    <div>
      <InputSection name="Item" classes=" flex-row gap-2 items-center">
        <TextInput
          name="name"
          bind:value={name}
          placeholder="Name"
          classes=" bg-white text-black"
          error={!form ? undefined : form.name.message}
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
            <Pageable
              endpoint="/inventory/categories"
              component={CategoryTable}
            />
            <button
              slot="button"
              class={`btn ${form && form.categories.message ? 'btn-error-red' : 'btn-primary'}`}
              >{determineButtonName(categories)}</button
            >
          </Modal>
        </div>
      </InputSection>
    </div>
    <div>
      <InputSection
        name="Quantity"
        classes=" flex-row gap-2 min-h-full items-center"
      >
        <TextInput
          name="quantity"
          bind:value={quantity}
          placeholder="Quantity"
          classes=" bg-white text-black"
          error={!form ? undefined : form.quantity.message}
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
