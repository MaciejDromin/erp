<script lang="ts">
  import Modal from '$lib/Modal.svelte'
  import Pageable from '$lib/Pageable.svelte'
  import { operationCategoriesStore } from '$lib/finances/stores/selectedOperationCategory'
  import OperationCategoryTable from '$lib/finances/operation-category/OperationCategoryTable.svelte'
  import MoneyOpearationTable from '$lib/finances/money-operation/MoneyOperationTable.svelte'
  import { onMount } from 'svelte'
  import { MoneyOperationType } from '$lib/finances/types/financialTypes'

  let categories: any[] = []
  let operationType: string = MoneyOperationType.INCOME

  onMount(() => {
    $operationCategoriesStore = []
  })

  operationCategoriesStore.subscribe((cat) => {
    categories = [...cat]
    categories = categories
  })

  const determineButtonName = (arr: any[]): string => {
    if (arr.length === 0) return 'select category'
    return `${arr.length} category selected`
  }
</script>

<div id="money-operation" class="flex flex-col gap-3 px-10 pt-10">
  <form method="POST" class="mx-auto flex flex-col gap-3 py-6">
    <div class="flex flex-row gap-3">
      <input
        name="amount"
        type="text"
        placeholder="Amount"
        class="input input-bordered input-primary w-full max-w-xs"
      />
      <input
        name="currencyCode"
        type="text"
        placeholder="Currency Code"
        class="input input-bordered input-primary w-full max-w-xs"
      />
    </div>
    <div class="flex flex-row gap-3">
      <input
        name="operationDescription"
        type="text"
        placeholder="Operation Description"
        class="input input-bordered input-primary w-full"
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
        name="categoryId"
        class="p-4 mr-auto hidden"
        bind:value={categories}
      >
        {#each categories as category}
          <option value={category}></option>
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
    <button class="btn btn-primary mx-auto">Add Row</button>
  </form>

  <Pageable
    endpoint="/finances/money-operation"
    component={MoneyOpearationTable}
  />
</div>
