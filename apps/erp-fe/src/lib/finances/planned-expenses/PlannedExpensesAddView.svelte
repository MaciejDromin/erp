<script lang="ts">
  import Pageable from '$lib/Pageable.svelte'
  import OperationCategoryTable from '$lib/finances/operation-category/OperationCategoryTable.svelte'
  import Modal from '$lib/Modal.svelte'
  import { genericStore } from '$lib/stores/genericStore.ts'
  import { onMount } from 'svelte'
  import { MoneyOperationType, Month } from '$lib/finances/types/financialTypes'
  import TextInput from '$lib/commons/TextInput.svelte'
  import TextArea from '$lib/commons/TextArea.svelte'
  import SelectInput from '$lib/commons/SelectInput.svelte'
  import InputSection from '$lib/commons/InputSection.svelte'

  export let data = undefined

  let plannedExpenseId = data === undefined ? undefined : data.plannedExpense.id
  let category =
    data === undefined
      ? undefined
      : { id: data.plannedExpense.operationCategory.id }
  let categories: any[] = category === undefined ? [] : [category]
  let selectedCategory =
    category === undefined ? undefined : JSON.stringify(category)
  let plannedAmount =
    data === undefined ? undefined : data.plannedExpense.plannedAmount.value
  let currencyCode =
    data === undefined
      ? undefined
      : data.plannedExpense.plannedAmount.currencyCode
  let operationDescription =
    data === undefined ? undefined : data.plannedExpense.operationDescription
  let plannedYear =
    data === undefined ? undefined : data.plannedExpense.plannedYear
  let plannedMonth =
    data === undefined ? undefined : data.plannedExpense.plannedMonth
  let buttonName = data === undefined ? 'Add' : 'Edit'

  onMount(() => {
    $genericStore = {}
  })

  genericStore.subscribe((catgz) => {
    if (
      catgz.finances !== undefined &&
      catgz.finances.categories !== undefined &&
      typeof catgz.finances.categories[Symbol.iterator] === 'function'
    ) {
      categories = [...catgz.finances.categories]
      categories = categories
      selectedCategory = JSON.stringify(categories[0])
    }
  })

  const determineButtonName = (arr: any[]): string => {
    if (arr.length === 0) return 'select category'
    return `${arr.length} category selected`
  }
</script>

<form method="POST" class="mx-auto flex flex-col gap-3">
  <input
    name="plannedExpenseId"
    type="text"
    class="hidden"
    bind:value={plannedExpenseId}
  />
  <div class="flex flex-row gap-3">
    <div>
      <InputSection name="Operation" classes=" flex-row gap-2">
        <TextInput
          name="plannedAmount"
          bind:value={plannedAmount}
          placeholder="Planned Amount"
          classes=" bg-white text-black"
        />
        <TextInput
          name="currencyCode"
          bind:value={currencyCode}
          placeholder="Currency Code"
          classes=" bg-white text-black"
        />
      </InputSection>
      <InputSection name="Period" classes=" flex-row gap-2 items-center">
        <TextInput
          name="plannedYear"
          bind:value={plannedYear}
          placeholder="Planned Year"
          classes=" bg-white text-black"
        />
        <SelectInput
          name="plannedMonth"
          bind:value={plannedMonth}
          classes=" bg-white text-black"
          options={Object.values(Month)}
        />
      </InputSection>
    </div>
    <div class="grow flex flex-col">
      <InputSection name="Category" classes=" flex-row gap-2">
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
        <div class="mx-auto">
          <Modal
            modalId="category_modal"
            buttonName={determineButtonName(categories)}
          >
            <Pageable
              endpoint="/finances/operation-category"
              component={OperationCategoryTable}
              additionalSearch={`&operationType=${MoneyOperationType.EXPENSES}`}
            />
            <button slot="button" class="btn btn-primary"
              >{determineButtonName(categories)}</button
            >
          </Modal>
        </div>
      </InputSection>
      <div>
        <InputSection name="Description" classes=" h-full w-full min-h-full">
          <TextArea
            name="operationDescription"
            bind:value={operationDescription}
            placeholder="Operation Description"
            classes=" bg-white text-black w-full min-h-full"
          />
        </InputSection>
      </div>
    </div>
  </div>
  <button class="btn btn-primary mx-auto">{buttonName}</button>
</form>
