<script lang="ts">
  import PartsTable from '$lib/inventory/parts/PartsTable.svelte'
  import ContractorsTable from '$lib/inventory/contractors/ContractorsTable.svelte'
  import Pageable from '$lib/Pageable.svelte'
  import Modal from '$lib/Modal.svelte'
  import { contractorsStore } from '$lib/inventory/stores/selectedContractors'
  import { onMount } from 'svelte'

  let contractors: any[] = []

  onMount(() => {
    $contractorsStore = []
  })

  contractorsStore.subscribe((contr) => {
    contractors = [...contr]
    contractors = contractors
  })

  const determineButtonName = (arr: any[]): string => {
    if (arr.length === 0) return 'select manufacturer'
    return `${arr.length} manufacturers selected`
  }
</script>

<div id="part" class="flex flex-col gap-3 px-10 pt-10">
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
        name="manufacturerId"
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

  <Pageable endpoint="/inventory/parts" component={PartsTable} />
</div>
