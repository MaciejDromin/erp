<script lang="ts">
  import ItemTable from '$lib/inventory/items/ItemTable.svelte'
  import Modal from '$lib/Modal.svelte'
  import Pageable from '$lib/Pageable.svelte'
  import { itemsStore } from '$lib/inventory/stores/selectedItems'
  import { onMount } from 'svelte'
  import ObjectValueTable from '$lib/finances/object-value/ObjectValueTable.svelte'
  import type { PageData } from './$types'
  import { ObjectType } from '$lib/finances/types/financialTypes'

  export let data: PageData

  let items: any[] = []

  onMount(() => {
    $itemsStore = []
  })

  itemsStore.subscribe((itm) => {
    items = [...itm]
    items = items
  })

  const determineButtonName = (arr: any[]): string => {
    if (arr.length === 0) return 'select item'
    return `${arr.length} item selected`
  }
</script>

<div id="object-value" class="flex flex-col gap-3 px-10 pt-10">
  <div class="stats shadow mx-auto">
    <div class="stat">
      <div class="stat-figure text-primary text-4xl">$</div>
      <div class="stat-title">Total Amount</div>
      <div class="stat-value text-primary">
        {data.objectsValue.totalAmount.value}
        {data.objectsValue.totalAmount.currencyCode}
      </div>
      <div class="stat-desc">Total amount of all objects</div>
    </div>

    <div class="stat">
      <div class="stat-figure text-secondary text-3xl">0..*</div>
      <div class="stat-title">Objects Count</div>
      <div class="stat-value text-secondary">
        {data.objectsValue.objectsCount}
      </div>
      <div class="stat-desc">How much objects were counted</div>
    </div>
  </div>
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
      <select
        multiple
        name="itemId"
        class="p-4 mr-auto hidden"
        bind:value={items}
      >
        {#each items as item}
          <option value={item} />
        {/each}
      </select>
      <div class="mr-auto">
        <Modal modalId="item_modal" buttonName={determineButtonName(items)}>
          <Pageable
            endpoint="/inventory/items"
            component={ItemTable}
            additionalSearch={data.objectIds.length === 0
              ? ''
              : `&objectIds=${data.objectIds.join(',')}`}
          />
        </Modal>
      </div>
      <button class="btn btn-primary">Add Row</button>
    </div>
  </form>

  <Pageable
    endpoint="/finances/object-value"
    additionalSearch={`&objectType=${ObjectType.PROPERTY}`}
    component={ObjectValueTable}
  />
</div>
