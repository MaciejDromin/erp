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

  export let data = undefined

  let propertyId = data === undefined ? undefined : data.property.id
  let name = data === undefined ? undefined : data.property.name
  let uniqueIdentifier =
    data === undefined ? undefined : data.property.uniqueIdentifier
  let landRegister = data === undefined ? undefined : data.property.landRegister
  let propertyType =
    data === undefined
      ? undefined
      : data.property.propertyInformation.propertyType
  let landClassification =
    data === undefined
      ? undefined
      : data.property.propertyInformation.landClassification
  let area =
    data === undefined
      ? undefined
      : data.property.propertyInformation.landArea.area
  let areaUnit =
    data === undefined
      ? undefined
      : data.property.propertyInformation.landArea.unit
  let address = data === undefined ? undefined : { id: data.property.addressId }
  let addresses: any[] = address === undefined ? [] : [address]
  let selectedAddress =
    address === undefined ? undefined : JSON.stringify(address)
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

<form method="POST" class="mx-auto flex flex-col gap-3 py-6">
  <input name="propertyId" type="text" class="hidden" bind:value={propertyId} />
  <div class="flex flex-row gap-3">
    <input
      name="name"
      type="text"
      bind:value={name}
      placeholder="Name"
      class="input input-bordered input-primary w-full max-w-xs"
    />
    <input
      name="uniqueIdentifier"
      type="text"
      bind:value={uniqueIdentifier}
      placeholder="Unique Identifier"
      class="input input-bordered input-primary w-full max-w-xs"
    />
    <input
      name="landRegister"
      type="text"
      bind:value={landRegister}
      placeholder="Land Register"
      class="input input-bordered input-primary w-full max-w-xs"
    />
  </div>

  <div class="flex flex-row gap-3">
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
    <select
      name="propertyType"
      bind:value={propertyType}
      class="select select-primary w-full max-w-xs"
    >
      {#each Object.values(PropertyType) as propertyType}
        <option value={propertyType}>{propertyType}</option>
      {/each}
    </select>
    <div class="mr-auto">
      <Modal
        modalId="address_modal"
        buttonName={determineButtonName(addresses)}
      >
        <Pageable endpoint="/inventory/addresses" component={AddressTable} />
      </Modal>
    </div>
    <div class="flex flex-row gap-3">
      <select
        name="landClassification"
        class="select select-primary w-full max-w-xs"
        bind:value={landClassification}
      >
        {#each Object.values(LandClassification) as landClassification}
          <option value={landClassification}>{landClassification}</option>
        {/each}
      </select>
      <input
        name="area"
        type="text"
        bind:value={area}
        placeholder="100,00"
        class="input input-bordered input-primary w-full max-w-xs"
      />
      <select
        name="areaUnit"
        bind:value={areaUnit}
        class="select select-primary w-full max-w-xs"
      >
        {#each Object.values(AreaUnit) as areaUnit}
          <option value={areaUnit}>{areaUnit}</option>
        {/each}
      </select>
    </div>
    <button class="btn btn-primary">{buttonName}</button>
  </div>
</form>
