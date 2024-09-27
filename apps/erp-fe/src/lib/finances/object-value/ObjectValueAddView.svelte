<script lang='ts'>
  import Modal from '$lib/Modal.svelte'
  import Pageable from '$lib/Pageable.svelte'
  import ItemTable from '$lib/inventory/items/ItemTable.svelte'
  import { itemsStore } from '$lib/inventory/stores/selectedItems'
  import { onMount } from 'svelte'

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
      <div>
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
    </div>
      <button class="btn btn-primary mx-auto">Add</button>
  </form>
