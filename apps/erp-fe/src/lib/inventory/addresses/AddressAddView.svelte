<script lang="ts">
  import TextInput from '$lib/commons/TextInput.svelte'
  import InputSection from '$lib/commons/InputSection.svelte'

  export let data = undefined

  let addressId = data === undefined ? '' : data.address.id
  let addressLine = data === undefined ? '' : data.address.addressLine
  let city = data === undefined ? '' : data.address.city
  let postalCode = data === undefined ? '' : data.address.postalCode
  let province = data === undefined ? '' : data.address.province
  let country = data === undefined ? '' : data.address.country
  let buttonName = data === undefined ? 'Add' : 'Edit'
</script>

<form method="POST" class="mx-auto flex flex-col gap-3">
  <input name="addressId" type="text" class="hidden" bind:value={addressId} />
  <InputSection name="Address" classes=" flex-row gap-2 w-fit mx-auto">
    <TextInput
      name="addressLine"
      bind:value={addressLine}
      placeholder="Address Line"
      classes=" bg-white text-black"
    />
    <TextInput
      name="postalCode"
      bind:value={postalCode}
      placeholder="Postal Code"
      classes=" bg-white text-black"
    />
  </InputSection>
  <InputSection name="Region" classes=" flex-row gap-2 w-fit mx-auto">
    <TextInput
      name="city"
      bind:value={city}
      placeholder="City"
      classes=" bg-white text-black"
    />
    <TextInput
      name="province"
      bind:value={province}
      placeholder="Province"
      classes=" bg-white text-black"
    />
  </InputSection>
  <InputSection name="Country" classes=" flex-row gap-2 w-fit mx-auto">
    <TextInput
      name="country"
      bind:value={country}
      placeholder="Country"
      classes=" bg-white text-black"
    />
  </InputSection>
  <button class="btn btn-primary mx-auto">{buttonName}</button>
</form>
