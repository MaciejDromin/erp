<script lang="ts">
  import ContractorsTable from '$lib/inventory/contractors/ContractorsTable.svelte'
  import PartsTable from '$lib/inventory/parts/PartsTable.svelte'
  import Modal from '$lib/Modal.svelte'
  import Pageable from '$lib/Pageable.svelte'
  import { genericStore } from '$lib/stores/genericStore.ts'
  import { onMount } from 'svelte'

  let contractors: any[] = []
  let selectedContractor
  let parts: any[] = []
  let partQuantity: any[] = []
  let partVal: string = ''

  onMount(() => {
    $genericStore = {}
  })

  genericStore.subscribe((objectz) => {
    if (
      objectz.inventory !== undefined &&
      objectz.inventory.contractors !== undefined &&
      typeof objectz.inventory.contractors[Symbol.iterator] === 'function'
    ) {
      contractors = [...objectz.inventory.contractors]
      contractors = contractors
      selectedContractor = JSON.stringify(contractors[0])
    }
    if (
      objectz.inventory !== undefined &&
      objectz.inventory.parts !== undefined &&
      typeof objectz.inventory.parts[Symbol.iterator] === 'function'
    ) {
      parts = [...objectz.inventory.parts]
      parts = parts
    }
  })

  const determineButtonName = (arr: any[], name: string): string => {
    if (arr.length === 0) return `select ${name}`
    return `${arr.length} ${name}s selected`
  }

  const updatePartsList = (partId: string, quantity: number) => {
    const index = partQuantity.findIndex((p) => p.id === partId)
    if (index === -1) {
      partQuantity.push({ id: partId, quantity: Number(quantity) })
    } else {
      partQuantity[index].quantity = Number(quantity)
    }
    partQuantity = [...partQuantity]
    partQuantity = partQuantity
    partVal = JSON.stringify(partQuantity)
  }
</script>

<form method="POST" class="mx-auto flex flex-col gap-3 py-6">
  <div class="flex flex-row gap-3">
    <input
      name="date"
      type="text"
      placeholder="Date"
      class="input input-bordered input-primary w-full max-w-xs"
    />
    <input
      name="odometer"
      type="text"
      placeholder="Odometer"
      class="input input-bordered input-primary w-full max-w-xs"
    />
    <select
      multiple
      name="contractor"
      class="p-4 mr-auto hidden"
      bind:value={selectedContractor}
    >
      {#each contractors as contractor}
        <option value={JSON.stringify(contractor)}></option>
      {/each}
    </select>
    <div class="mr-auto">
      <Modal
        modalId="contractor_modal"
        buttonName={determineButtonName(contractors, 'contractor')}
      >
        <Pageable
          endpoint="/inventory/contractors"
          component={ContractorsTable}
        />
      </Modal>
    </div>
    <div class="mr-auto">
      <Modal
        modalId="parts_modal"
        buttonName={determineButtonName(parts, 'part')}
      >
        <Pageable endpoint="/inventory/parts" component={PartsTable} />
      </Modal>
    </div>
  </div>
  <div class="flex flex-col gap-3">
    <input name="parts" type="text" class="hidden" bind:value={partVal} />
    <h2 class="mx-auto my-6 text-3xl text-black">Parts</h2>
    {#each parts as part}
      <input
        type="text"
        placeholder={part.name + ' quantity'}
        class="input input-bordered input-primary w-full max-w-xs mx-auto"
        on:change={(e) => updatePartsList(part.id, e.target.value)}
      />
    {/each}
    <button class="btn btn-primary mx-auto">Add Row</button>
  </div>
</form>
