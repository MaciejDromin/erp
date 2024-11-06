<script lang="ts">
  export let data = undefined

  let addressId = data === undefined ? '' : data.address.id
  let addressLine = data === undefined ? '' : data.address.addressLine
  let city = data === undefined ? '' : data.address.city
  let postalCode = data === undefined ? '' : data.address.postalCode
  let province = data === undefined ? '' : data.address.province
  let country = data === undefined ? '' : data.address.country
  let buttonName = data === undefined ? 'Add' : 'Edit'
</script>
<form method="POST" class="mx-auto flex flex-row gap-3 py-6">
  <input name="addressId" type="text" class="hidden" bind:value={addressId} />
  <input
    name="addressLine"
    bind:value={addressLine}
    type="text"
    placeholder="Address Line"
    class="input input-bordered input-primary w-full max-w-xs"
  />
  <input
    name="city"
    bind:value={city}
    type="text"
    placeholder="City"
    class="input input-bordered input-primary w-full max-w-xs"
  />
  <input
    name="postalCode"
    bind:value={postalCode}
    type="text"
    placeholder="Postal Code"
    class="input input-bordered input-primary w-full max-w-xs"
  />
  <input
    name="province"
    bind:value={province}
    type="text"
    placeholder="Province"
    class="input input-bordered input-primary w-full max-w-xs"
  />
  <input
    name="country"
    bind:value={country}
    type="text"
    placeholder="Country"
    class="input input-bordered input-primary w-full max-w-xs"
  />
  <button class="btn btn-primary">{buttonName} Address</button>
</form>
