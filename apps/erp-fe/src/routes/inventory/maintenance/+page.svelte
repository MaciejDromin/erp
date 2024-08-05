<script lang="ts">
  import MaintenanceTable from '$lib/inventory/maintenance/MaintenanceTable.svelte'
  import ContractorsTable from '$lib/inventory/contractors/ContractorsTable.svelte'
  import PartsTable from '$lib/inventory/parts/PartsTable.svelte'
  import Pageable from '$lib/Pageable.svelte'
  import Modal from '$lib/Modal.svelte'
  import { contractorsStore } from '$lib/inventory/stores/selectedContractors'
  import { partsStore } from '$lib/inventory/stores/selectedParts'
  import { onMount } from 'svelte'

  let contractors: any[] = []
  let parts: any[] = []
  let partQuantity: any[] = []
  let partVal:string = ""

  onMount(() => {
    $contractorsStore = []
    $partsStore = []
  })

  contractorsStore.subscribe((contr) => {
    contractors = [...contr]
    contractors = contractors
  })

  partsStore.subscribe((part) => {
    parts = [...part]
    parts = parts
  })

  const determineButtonName = (arr: any[], name: string): string => {
    if (arr.length === 0) return `select ${name}`
    return `${arr.length} ${name}s selected`
  }
  
  const updatePartsList = (partId: string, quantity: number) => {
    const index = partQuantity.findIndex(p => p.id === partId)
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

<div id="maintenance" class="flex flex-col gap-3 px-10 pt-10">
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
        name="contractorId"
        class="p-4 mr-auto hidden"
        bind:value={contractors}
      >
        {#each contractors as contractor}
          <option value={contractor}></option>
        {/each}
      </select>
      <div class="mr-auto">
        <Modal
          modalId="contractor_modal"
          buttonName={determineButtonName(contractors, "contractor")}
        >
          <Pageable endpoint="/inventory/contractors" component={ContractorsTable} />
        </Modal>
      </div>
      <div class="mr-auto">
        <Modal
          modalId="parts_modal"
          buttonName={determineButtonName(parts, "part")}
        >
          <Pageable endpoint="/inventory/parts" component={PartsTable} />
        </Modal>
      </div>
    </div>
    <div class="flex flex-col gap-3">
      <input
        name="parts"
        type="text"
        class="hidden"
        bind:value={partVal}
      />
      <h2 class="mx-auto my-6 text-3xl text-black">Parts</h2>
      {#each parts as part}
        <input
          type="text"
          placeholder={JSON.parse(part).name + " quantity"}
          class="input input-bordered input-primary w-full max-w-xs mx-auto"
          on:change={(e) => updatePartsList(JSON.parse(part).id, e.target.value)}
        />
      {/each}
      <button class="btn btn-primary mx-auto">Add Row</button>
    </div>
  </form>

  <Pageable endpoint="/inventory/maintenance" component={MaintenanceTable} />
</div>
