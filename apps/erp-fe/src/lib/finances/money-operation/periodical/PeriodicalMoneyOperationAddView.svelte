<script lang="ts">
  import { genericStore } from '$lib/stores/genericStore.ts'
  import { onMount } from 'svelte'
  import { MoneyOperationType, Month } from '$lib/finances/types/financialTypes'
  import Pageable from '$lib/Pageable.svelte'
  import Modal from '$lib/Modal.svelte'
  import OperationCategoryTable from '$lib/finances/operation-category/OperationCategoryTable.svelte'

  export let data = undefined

  let periodicalOperationId = data === undefined ? undefined : data.operation.id
  let category =
    data === undefined ? undefined : { id: data.operation.operationCategory.id }
  let categories: any[] = category === undefined ? [] : [category]
  let selectedCategory =
    category === undefined ? undefined : JSON.stringify(category)
  let amount = data === undefined ? undefined : data.operation.amount.value
  let currencyCode =
    data === undefined ? undefined : data.operation.amount.currencyCode
  let operationDescription =
    data === undefined ? undefined : data.operation.operationDescription
  let operationType =
    data === undefined
      ? MoneyOperationType.INCOME
      : data.operation.operationType
  let repetitionPeriod =
    data === undefined ? undefined : data.operation.repetitionPeriod
  let nextApplicableMonth =
    data === undefined ? undefined : data.operation.nextApplicableMonth
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
    name="periodicalOperationId"
    type="text"
    class="hidden"
    bind:value={periodicalOperationId}
  />
  <div class="flex flex-row gap-3">
    <input
      name="amount"
      type="text"
      bind:value={amount}
      placeholder="Amount"
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
    <select
      name="nextApplicableMonth"
      bind:value={nextApplicableMonth}
      class="select select-primary w-full max-w-xs"
    >
      {#each Object.values(Month) as month}
        <option value={month}>{month}</option>
      {/each}
    </select>
    <input
      name="repetitionPeriod"
      type="text"
      bind:value={repetitionPeriod}
      placeholder="Repetition Period"
      class="input input-bordered input-primary w-full max-w-xs"
    />
  </div>

  <div class="flex flex-row gap-3">
    <select
      name="operationType"
      class="select select-primary w-full max-w-xs"
      bind:value={operationType}
    >
      {#each Object.values(MoneyOperationType) as operationType}
        <option value={operationType}>{operationType}</option>
      {/each}
    </select>
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
          endpoint="/finances/operation-category"
          component={OperationCategoryTable}
          additionalSearch={`&operationType=${operationType}`}
        />
      </Modal>
    </div>
  </div>
  <button class="btn btn-primary mx-auto">{buttonName}</button>
</form>
