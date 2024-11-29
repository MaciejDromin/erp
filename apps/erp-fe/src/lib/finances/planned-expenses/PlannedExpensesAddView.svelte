<script lang="ts">
  import Pageable from '$lib/Pageable.svelte'
  import OperationCategoryTable from '$lib/finances/operation-category/OperationCategoryTable.svelte'
  import Modal from '$lib/Modal.svelte'
  import { genericStore } from '$lib/stores/genericStore.ts'
  import { onMount } from 'svelte'
  import { MoneyOperationType, Month } from '$lib/finances/types/financialTypes'

  export let data = undefined

  let plannedExpenseId =
    data === undefined ? undefined : data.plannedExpense.id
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

<form method="POST" class="mx-auto flex flex-col gap-3 py-6">
  <input
    name="plannedExpenseId"
    type="text"
    class="hidden"
    bind:value={plannedExpenseId}
  />
  <div class="flex flex-row gap-3">
    <input
      name="plannedAmount"
      type="text"
      bind:value={plannedAmount}
      placeholder="Planned Amount"
      class="input input-bordered input-primary w-full max-w-xs"
    />
    <input
      name="currencyCode"
      type="text"
      bind:value={currencyCode}
      placeholder="Currency Code"
      class="input input-bordered input-primary w-full max-w-xs"
    />
  </div>
  <div class="flex flex-row gap-3">
    <input
      name="operationDescription"
      type="text"
      bind:value={operationDescription}
      placeholder="Operation Description"
      class="input input-bordered input-primary w-full"
    />
  </div>
  <div class="flex flex-row gap-3">
    <input
      name="plannedYear"
      type="text"
      bind:value={plannedYear}
      placeholder="Planned Year"
      class="input input-bordered input-primary w-full max-w-xs"
    />
    <select
      name="plannedMonth"
      bind:value={plannedMonth}
      class="select select-primary w-full max-w-xs"
    >
      {#each Object.values(Month) as month}
        <option value={month}>{month}</option>
      {/each}
    </select>
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
      </Modal>
    </div>
  </div>
  <button class="btn btn-primary mx-auto">{buttonName}</button>
</form>
