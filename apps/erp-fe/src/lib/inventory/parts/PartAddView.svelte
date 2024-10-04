<script lang="ts">
  import ContractorsTable from '$lib/inventory/contractors/ContractorsTable.svelte'
  import Modal from '$lib/Modal.svelte'
  import Pageable from '$lib/Pageable.svelte'
  import { onMount } from 'svelte'
  import { genericStore } from '$lib/stores/genericStore.ts'

  let contractors: any[] = []
  let selectedContractor

  onMount(() => {
    $genericStore = {}
  })

  genericStore.subscribe((contr) => {
    if (
      contr.inventory !== undefined &&
      contr.inventory.contractors !== undefined &&
      typeof contr.inventory.contractors[Symbol.iterator] === 'function'
    ) {
      contractors = [...contr.inventory.contractors]
      contractors = contractors
      selectedContractor = JSON.stringify(contractors[0])
    }
  })

  const determineButtonName = (arr: any[]): string => {
    if (arr.length === 0) return 'select manufacturer'
    return `${arr.length} manufacturers selected`
  }
</script>

<form method="POST" class="mx-auto flex flex-col gap-3 py-6">
  <div class="flex flex-row gap-3">
    <input
      name="name"
      type="text"
      placeholder="Name"
      class="input input-bordered input-primary w-full max-w-xs"
    />
    <input
      name="partNumber"
      type="text"
      placeholder="Part Number"
      class="input input-bordered input-primary w-full max-w-xs"
    />
    <select
      multiple
      name="manufacturer"
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
        buttonName={determineButtonName(contractors)}
      >
        <Pageable
          endpoint="/inventory/contractors"
          component={ContractorsTable}
        />
      </Modal>
    </div>
    <button class="btn btn-primary">Add Row</button>
  </div>
</form>
