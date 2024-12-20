<script lang="ts">
  import ContractorsTable from '$lib/inventory/contractors/ContractorsTable.svelte'
  import Modal from '$lib/Modal.svelte'
  import Pageable from '$lib/Pageable.svelte'
  import { onMount } from 'svelte'
  import { genericStore } from '$lib/stores/genericStore.ts'
  import TextInput from '$lib/commons/TextInput.svelte'
  import InputSection from '$lib/commons/InputSection.svelte'
  import { extractValue } from '$lib/scripts/dataExtractor.ts'
  import { idObjWrapper } from '$lib/scripts/valueWrappers.ts'
  import partKeys from '$lib/inventory/types/partKeys.ts'

  export let data = undefined
  export let form

  let partId = extractValue(data, form, partKeys.id)
  let name = extractValue(data, form, partKeys.name)
  let partNumber = extractValue(data, form, partKeys.partNumber)
  let contractor = extractValue(data, form, partKeys.contractor, null, idObjWrapper)
  let selectedContractor = JSON.stringify(contractor)
  let contractors: any[] = contractor === null ? [] : [contractor]
  let buttonName = data === undefined ? 'Add' : 'Edit'

  console.log(data)
  console.log(form)

  console.log(contractor)

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
  <input name="partId" type="text" class="hidden" bind:value={partId} />
  <InputSection name="Part Details" classes=" flex-row gap-2 w-fit mx-auto">
    <TextInput
      name="name"
      bind:value={name}
      placeholder="Name"
      classes=" bg-white text-black"
      error={!form ? undefined : form.name.message}
    />
    <TextInput
      name="partNumber"
      bind:value={partNumber}
      placeholder="Part Number"
      classes=" bg-white text-black"
      error={!form ? undefined : form.partNumber.message}
    />
  </InputSection>
  <InputSection name="Manufacturer" classes=" flex-row gap-2 w-fit mx-auto">
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
        <button
          slot="button"
          class={`btn ${form && form.manufacturer.message ? 'btn-error-red' : 'btn-primary'}`}
          >{determineButtonName(contractors)}</button
        >
      </Modal>
    </div>
  </InputSection>
  <button class="btn btn-primary mx-auto">{buttonName}</button>
</form>
