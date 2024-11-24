<script lang="ts">
  import Modal from '$lib/Modal.svelte'
  import Pageable from '$lib/Pageable.svelte'
  import { genericStore } from '$lib/stores/genericStore.ts'
  import { onMount } from 'svelte'

  export let config

  let data = config.data

  let objectValueId = data.objectValue === undefined ? undefined : data.objectValue.uuid
  let obj = data.objectValue  === undefined ? undefined : { id: data.objectValue.objectId }
  let objects: any[] = obj === undefined ? [] : [obj]
  let selectedObject = obj === undefined ? undefined : JSON.stringify(obj)
  let amount = data.objectValue  === undefined ? undefined : data.objectValue.amount.value
  let currency =
    data.objectValue  === undefined ? undefined : data.objectValue.amount.currencyCode
  let buttonName = data.objectValue  === undefined ? 'Add' : 'Edit'

  onMount(() => {
    $genericStore = {}
  })

  genericStore.subscribe((objectz) => {
    if (
      objectz.inventory !== undefined &&
      objectz.inventory[config.storeKey] !== undefined &&
      typeof objectz.inventory[config.storeKey][Symbol.iterator] === 'function'
    ) {
      objects = [...objectz.inventory[config.storeKey]]
      objects = objects
      selectedObject = JSON.stringify(objects[0])
    }
  })

  const determineButtonName = (arr: any[]): string => {
    if (arr.length === 0) return `select ${config.buttonName}`
    return `${arr.length} ${config.buttonName} selected`
  }
</script>

<form method="POST" class="mx-auto flex flex-col gap-3 py-6">
  <input
    name="objectValueId"
    type="text"
    class="hidden"
    bind:value={objectValueId}
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
      bind:value={currency}
      placeholder="Currency Code"
      class="input input-bordered input-primary w-full max-w-xs"
    />
  </div>

  <div class="flex flex-row gap-3">
    <select
      multiple
      name="object"
      class="p-4 mr-auto hidden"
      bind:value={selectedObject}
    >
      {#each objects as object}
        <option value={JSON.stringify(object)} />
      {/each}
    </select>
    <div>
      <Modal modalId="object_modal" buttonName={determineButtonName(objects)}>
        <Pageable
          endpoint={config.endpoint}
          component={config.component}
          additionalSearch={config.data.objectIds.length === 0
            ? ''
            : `&objectIds=${config.data.objectIds.join(',')}`}
        />
      </Modal>
    </div>
  </div>
  <button class="btn btn-primary mx-auto">{buttonName}</button>
</form>
