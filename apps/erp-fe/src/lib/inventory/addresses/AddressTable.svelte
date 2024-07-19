<script lang="ts">
  import { onDestroy, onMount } from 'svelte'
  import { addressesStore } from '../stores/selectedAddresses'

  export let data: any = undefined
  let selectedAddressesIds: Map<string, string> = new Map()

  onMount(() => {
    $addressesStore.forEach((cat) => selectedAddressesIds.set(cat, 'ok'))
  })

  onDestroy(() => {
    $addressesStore = Array.from(selectedAddressesIds.keys())
  })

  const updateAddressesList = (addressId: string) => {
    if (selectedAddressesIds.has(addressId)) {
      selectedAddressesIds.delete(addressId)
    } else {
      selectedAddressesIds.set(addressId, 'ok')
    }
    selectedAddressesIds = selectedAddressesIds
  }

  const addressesSelectedStyles = (
    addressesMap: Map<string, string>,
    addressId: string
  ): string => {
    if (!addressesMap.has(addressId)) return ''
    return 'bg-indigo-600 text-white'
  }

  const determineEvenBgColor = (
    addressesMap: Map<string, string>,
    addressId: string
  ): string => {
    if (!addressesMap.has(addressId)) return 'even:bg-black'
    return 'even:bg-indigo-600'
  }
</script>

<div class="overflow-x-auto text-primary-content mx-auto">
  <!-- <table class="table table-zebra">  -->
  <table class="table">
    <!-- head -->
    <thead class="text-primary-content">
      <tr>
        <th>ID</th>
        <th>Address Line</th>
        <th>City</th>
        <th>Postal Code</th>
        <th>Province</th>
        <th>Country</th>
      </tr>
    </thead>
    <tbody>
      {#if data !== undefined}
        {#each data.content as address}
          <tr
            class={`hover:bg-indigo-400 hover:text-black even:text-white hover:even:text-black hover:even:bg-indigo-400 cursor-pointer ease-in transition-all duration-200
                        ${addressesSelectedStyles(
                          selectedAddressesIds,
                          address.id
                        )}
                        ${determineEvenBgColor(
                          selectedAddressesIds,
                          address.id
                        )}`}
            on:click={() => updateAddressesList(address.id)}
          >
            <td>{address.id}</td>
            <td>{address.addressLine}</td>
            <td>{address.city}</td>
            <td>{address.country}</td>
            <td>{address.province}</td>
            <td>{address.postalCode}</td>
          </tr>
        {/each}
      {/if}
    </tbody>
  </table>
</div>
<!-- <style>
    .bg-indigo-600-important {
        --tw-bg-opacity: 1;
        background-color: rgb(79 70 229 / var(--tw-bg-opacity)) !important;
    }
</style> -->
