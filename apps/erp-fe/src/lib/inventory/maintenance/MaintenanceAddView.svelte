<script lang="ts">
  import ContractorsTable from '$lib/inventory/contractors/ContractorsTable.svelte'
  import PartsTable from '$lib/inventory/parts/PartsTable.svelte'
  import Modal from '$lib/Modal.svelte'
  import Pageable from '$lib/Pageable.svelte'
  import { genericStore } from '$lib/stores/genericStore.ts'
  import { onMount } from 'svelte'
  import TextInput from '$lib/commons/TextInput.svelte'
  import InputSection from '$lib/commons/InputSection.svelte'
  import { extractValue } from '$lib/scripts/dataExtractor.ts'
  import { idObjWrapper, partMapper } from '$lib/scripts/valueWrappers.ts'
  import maintenanceKeys from '$lib/inventory/types/maintenanceKeys.ts'

  export let data = undefined
  export let form

  let maintenanceId = extractValue(data, form, maintenanceKeys.id)
  let date = extractValue(data, form, maintenanceKeys.date)
  let odometer = extractValue(data, form, maintenanceKeys.odometer)
  let contractor = extractValue(
    data,
    form,
    maintenanceKeys.contractor,
    null,
    idObjWrapper
  )
  let contractors: any[] = contractor === null ? [] : [contractor]
  let selectedContractor = JSON.stringify(contractor)
  let partsQuantityMap = extractValue(
    data,
    form,
    maintenanceKeys.parts,
    undefined,
    partMapper
  )
  let parts: any[] = extractValue(data, form, maintenanceKeys.partsData, [])
  let partQuantity: any[] = parts.map((p) => ({
    id: p.id,
    quantity: partsQuantityMap.get(p.id),
  }))
  let partVal: string = JSON.stringify(partQuantity)
  let partsJ = JSON.stringify(parts)
  let buttonName = data === undefined ? 'Add' : 'Edit'

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

  const getValue = (partId: string) => {
    const index = partQuantity.findIndex((p) => p.id === partId)
    if (index === -1) {
      return 1
    }
    return partQuantity[index].quantity
  }

  const partFailed = (partId: string) => {
    if (form && form[partId].message) {
      return 'input-error-red focus:border-error-red focus:outline-error-red'
    }
    return 'input-primary'
  }

  const updateParts = (parts) => {
    // add new parts
    parts.forEach((part) => {
      const index = partQuantity.findIndex((p) => p.id === part.id)
      if (index === -1) {
        partQuantity.push({ id: part.id, quantity: 1 })
      }
    })
    // delete non existsing
    partQuantity.forEach((part, index) => {
      const indx = parts.findIndex((p) => p.id === part.id)
      if (indx === -1) {
        partQuantity.splice(index, 1)
      }
    })
    partQuantity = [...partQuantity]
    partQuantity = partQuantity
    partVal = JSON.stringify(partQuantity)
    partsJ = JSON.stringify(parts)
  }

  $: parts, updateParts(parts)
</script>

<form method="POST" class="mx-auto flex flex-col gap-3 py-6">
  <input
    name="maintenanceId"
    type="text"
    class="hidden"
    bind:value={maintenanceId}
  />
  <input name="partsData" type="text" class="hidden" bind:value={partsJ} />
  <InputSection name="Details" classes=" flex-row gap-2 w-fit mx-auto">
    <TextInput
      name="date"
      bind:value={date}
      placeholder="Date"
      classes=" bg-white text-black"
      error={!form ? undefined : form.date.message}
    />
    <TextInput
      name="odometer"
      bind:value={odometer}
      placeholder="Odometer"
      classes=" bg-white text-black"
      error={!form ? undefined : form.odometer.message}
    />
  </InputSection>
  <InputSection
    name="Parts & Contractor"
    classes=" flex-row gap-2 w-fit mx-auto"
  >
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
    <Modal
      modalId="contractor_modal"
      buttonName={determineButtonName(contractors, 'contractor')}
    >
      <Pageable
        endpoint="/inventory/contractors"
        component={ContractorsTable}
      />
      <button
        slot="button"
        class={`btn ${form && form.contractor.message ? 'btn-error-red' : 'btn-primary'}`}
        >{determineButtonName(contractors, 'contractor')}</button
      >
    </Modal>
    <Modal
      modalId="parts_modal"
      buttonName={determineButtonName(parts, 'part')}
    >
      <Pageable endpoint="/inventory/parts" component={PartsTable} />
      <button slot="button" class="btn btn-primary"
        >{determineButtonName(parts, 'part')}</button
      >
    </Modal>
  </InputSection>
  <div class="flex flex-col gap-3">
    <input name="parts" type="text" class="hidden" bind:value={partVal} />
    <h2 class="mx-auto my-6 text-3xl text-black">Parts</h2>
    {#each parts as part}
      <InputSection name={part.name} classes=" flex-row gap-2 w-fit mx-auto">
        <input
          type="text"
          placeholder={part.name + ' quantity'}
          value={getValue(part.id)}
          class={`input input-bordered ${partFailed(part.id)} w-full max-w-xs mx-auto bg-white text-black`}
          on:change={(e) => updatePartsList(part.id, e.target.value)}
        />
      </InputSection>
    {/each}
    <button class="btn btn-primary mx-auto">{buttonName}</button>
  </div>
</form>
