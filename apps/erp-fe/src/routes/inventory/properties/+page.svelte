<script lang="ts">
  import AddressTable from '$lib/inventory/addresses/AddressTable.svelte'
  import PropertyTable from '$lib/inventory/properties/PropertyTable.svelte'
  import Modal from '$lib/Modal.svelte'
  import Pageable from '$lib/Pageable.svelte'
  import { addressesStore } from '$lib/inventory/stores/selectedAddresses'
  import { onMount } from 'svelte'
  import {
    PropertyType,
    LandClassification,
    AreaUnit,
  } from '$lib/inventory/types/inventoryTypes'

  let addresses: any[] = []

  onMount(() => {
    $addressesStore = []
  })

  addressesStore.subscribe((addr) => {
    addresses = [...addr]
    addresses = addresses
  })

  const determineButtonName = (arr: any[]): string => {
    if (arr.length === 0) return 'select addresses'
    return `${arr.length} addresses selected`
  }
</script>

<div id="property" class="flex flex-col gap-3 px-10 pt-10">
  <form method="POST" class="mx-auto flex flex-col gap-3 py-6">
    <div class="flex flex-row gap-3">
      <input
        name="name"
        type="text"
        placeholder="Name"
        class="input input-bordered input-primary w-full max-w-xs"
      />
      <input
        name="uniqueIdentifier"
        type="text"
        placeholder="Unique Identifier"
        class="input input-bordered input-primary w-full max-w-xs"
      />
    </div>

    <div class="flex flex-row gap-3">
      <select
        multiple
        name="addressId"
        class="p-4 mr-auto hidden"
        bind:value={addresses}
      >
        {#each addresses as address}
          <option value={address}></option>
        {/each}
      </select>
      <select name="propertyType" class="select select-primary w-full max-w-xs">
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
        >
          {#each Object.values(LandClassification) as landClassification}
            <option value={landClassification}>{landClassification}</option>
          {/each}
        </select>
        <input
          name="area"
          type="text"
          placeholder="100,00"
          class="input input-bordered input-primary w-full max-w-xs"
        />
        <select name="areaUnit" class="select select-primary w-full max-w-xs">
          {#each Object.values(AreaUnit) as areaUnit}
            <option value={areaUnit}>{areaUnit}</option>
          {/each}
        </select>
      </div>
      <button class="btn btn-primary">Add Row</button>
    </div>
  </form>

  <Pageable endpoint="/inventory/properties" component={PropertyTable} />
</div>
