<script lang="ts">
  import Pageable from '$lib/Pageable.svelte'
  import AddressTable from '$lib/inventory/addresses/AddressTable.svelte'
  import { addressesStore } from '$lib/inventory/stores/selectedAddresses'
  import { onMount } from 'svelte'

  onMount(() => {
    $addressesStore = []
  })
</script>

<div id="address" class="flex flex-col gap-3 px-10 pt-10">
  <form method="POST" class="mx-auto flex flex-row gap-3 py-6">
    <input
      name="addressLine"
      type="text"
      placeholder="Address Line"
      class="input input-bordered input-primary w-full max-w-xs"
    />
    <input
      name="city"
      type="text"
      placeholder="City"
      class="input input-bordered input-primary w-full max-w-xs"
    />
    <input
      name="postalCode"
      type="text"
      placeholder="Postal Code"
      class="input input-bordered input-primary w-full max-w-xs"
    />
    <input
      name="province"
      type="text"
      placeholder="Province"
      class="input input-bordered input-primary w-full max-w-xs"
    />
    <input
      name="country"
      type="text"
      placeholder="Country"
      class="input input-bordered input-primary w-full max-w-xs"
    />
    <button class="btn btn-primary">Add Row</button>
  </form>

  <Pageable endpoint="/inventory/addresses" component={AddressTable} />
</div>
