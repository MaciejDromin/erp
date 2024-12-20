<script lang="ts">
  import Modal from '$lib/Modal.svelte'
  import Pageable from '$lib/Pageable.svelte'
  import AddressTable from '$lib/inventory/addresses/AddressTable.svelte'
  import { genericStore } from '$lib/stores/genericStore.ts'
  import { onMount } from 'svelte'
  import {
    PropertyType,
    LandClassification,
    AreaUnit,
  } from '$lib/inventory/types/inventoryTypes'
  import TextInput from '$lib/commons/TextInput.svelte'
  import SelectInput from '$lib/commons/SelectInput.svelte'
  import InputSection from '$lib/commons/InputSection.svelte'
  import { extractValue } from '$lib/scripts/dataExtractor.ts'
  import { idObjWrapper } from '$lib/scripts/valueWrappers.ts'
  import propertyKeys from '$lib/inventory/types/propertyKeys.ts'

  export let data = undefined
  export let form

  let propertyId = extractValue(data, form, propertyKeys.id)
  let name = extractValue(data, form, propertyKeys.name)
  let uniqueIdentifier = extractValue(data, form, propertyKeys.uniqueIdentifier)
  let landRegister = extractValue(data, form, propertyKeys.landRegister)
  let propertyType = extractValue(data, form, propertyKeys.propertyType)
  let landClassification = extractValue(
    data,
    form,
    propertyKeys.landClassification
  )
  let area = extractValue(data, form, propertyKeys.area)
  let areaUnit = extractValue(data, form, propertyKeys.unit)
  let address = extractValue(
    data,
    form,
    propertyKeys.address,
    null,
    idObjWrapper
  )
  let addresses: any[] = address === null ? [] : [address]
  let selectedAddress = JSON.stringify(address)
  let buttonName = data === undefined ? 'Add' : 'Edit'

  onMount(() => {
    $genericStore = {}
  })

  genericStore.subscribe((contr) => {
    if (
      contr.inventory !== undefined &&
      contr.inventory.addresses !== undefined &&
      typeof contr.inventory.addresses[Symbol.iterator] === 'function'
    ) {
      addresses = [...contr.inventory.addresses]
      addresses = addresses
      selectedAddress = JSON.stringify(addresses[0])
    }
  })

  const determineButtonName = (arr: any[]): string => {
    if (arr.length === 0) return 'select addresses'
    return `${arr.length} addresses selected`
  }
</script>

<form method="POST" class="mx-auto flex flex-col gap-3">
  <input name="propertyId" type="text" class="hidden" bind:value={propertyId} />
  <div class="flex flex-row gap-3 mx-auto">
    <InputSection name="Property" classes=" flex-row gap-2 items-center">
      <TextInput
        name="name"
        bind:value={name}
        placeholder="Name"
        classes=" bg-white text-black"
        error={!form ? undefined : form.name.message}
      />
      <SelectInput
        name="propertyType"
        bind:value={propertyType}
        classes=" bg-white text-black"
        options={Object.values(PropertyType)}
      />
    </InputSection>
    <InputSection name="Identification" classes=" flex-col gap-2">
      <div class="flex flex-row gap-3">
        <TextInput
          name="uniqueIdentifier"
          bind:value={uniqueIdentifier}
          placeholder="Unique Identifier"
          classes=" bg-white text-black"
          error={!form ? undefined : form.uniqueIdentifier.message}
        />
        <TextInput
          name="landRegister"
          bind:value={landRegister}
          placeholder="Land Register"
          classes=" bg-white text-black"
          error={!form ? undefined : form.landRegister.message}
        />
      </div>
      <select
        multiple
        name="address"
        class="p-4 mr-auto hidden"
        bind:value={selectedAddress}
      >
        {#each addresses as address}
          <option value={JSON.stringify(address)}></option>
        {/each}
      </select>
      <div class="mx-auto">
        <Modal
          modalId="address_modal"
          buttonName={determineButtonName(addresses)}
        >
          <Pageable endpoint="/inventory/addresses" component={AddressTable} />
          <button
            slot="button"
            class={`btn ${form && form.address.message ? 'btn-error-red' : 'btn-primary'}`}
            >{determineButtonName(addresses)}</button
          >
        </Modal>
      </div>
    </InputSection>
  </div>
  <InputSection name="Property Details" classes=" flex-row gap-2 w-fit mx-auto">
    <SelectInput
      name="landClassification"
      bind:value={landClassification}
      classes=" bg-white text-black"
      options={Object.values(LandClassification)}
    />
    <TextInput
      name="area"
      bind:value={area}
      placeholder="100,00"
      classes=" bg-white text-black"
      error={!form ? undefined : form.propertyInformation.landArea.area.message}
    />
    <SelectInput
      name="areaUnit"
      bind:value={areaUnit}
      classes=" bg-white text-black"
      options={Object.values(AreaUnit)}
    />
  </InputSection>
  <button class="btn btn-primary mx-auto">{buttonName}</button>
</form>
