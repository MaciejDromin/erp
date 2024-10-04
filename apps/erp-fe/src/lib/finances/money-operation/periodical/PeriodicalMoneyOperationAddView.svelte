<script lang="ts">
  import { genericStore } from '$lib/stores/genericStore.ts'
  import { onMount } from 'svelte'
  import { MoneyOperationType, Month } from '$lib/finances/types/financialTypes'
  import Pageable from '$lib/Pageable.svelte'
  import Modal from '$lib/Modal.svelte'
  import OperationCategoryTable from '$lib/finances/operation-category/OperationCategoryTable.svelte'

  let categories: any[] = []
  let selectedCategory
  let operationType: string = MoneyOperationType.INCOME

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
      name="nextApplicableMonth"
      class="select select-primary w-full max-w-xs"
    >
      {#each Object.values(Month) as month}
        <option value={month}>{month}</option>
      {/each}
    </select>
    <input
      name="repetitionPeriod"
      type="text"
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
  <button class="btn btn-primary mx-auto">Add</button>
</form>
