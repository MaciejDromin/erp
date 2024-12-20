<script lang="ts">
  import TextInput from '$lib/commons/TextInput.svelte'
  import InputSection from '$lib/commons/InputSection.svelte'
  import { extractValue } from '$lib/scripts/dataExtractor.ts'
  import addressKeys from '$lib/inventory/types/addressKeys.ts'

  export let data = undefined
  export let form

  let addressId = extractValue(data, form, addressKeys.id)
  let addressLine = extractValue(data, form, addressKeys.addressLine)
  let city = extractValue(data, form, addressKeys.city)
  let postalCode = extractValue(data, form, addressKeys.postalCode)
  let province = extractValue(data, form, addressKeys.province)
  let country = extractValue(data, form, addressKeys.country)
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
      error={!form ? undefined : form.addressLine.message}
    />
    <TextInput
      name="postalCode"
      bind:value={postalCode}
      placeholder="Postal Code"
      classes=" bg-white text-black"
      error={!form ? undefined : form.postalCode.message}
    />
  </InputSection>
  <InputSection name="Region" classes=" flex-row gap-2 w-fit mx-auto">
    <TextInput
      name="city"
      bind:value={city}
      placeholder="City"
      classes=" bg-white text-black"
      error={!form ? undefined : form.city.message}
    />
    <TextInput
      name="province"
      bind:value={province}
      placeholder="Province"
      classes=" bg-white text-black"
      error={!form ? undefined : form.province.message}
    />
  </InputSection>
  <InputSection name="Country" classes=" flex-row gap-2 w-fit mx-auto">
    <TextInput
      name="country"
      bind:value={country}
      placeholder="Country"
      classes=" bg-white text-black"
      error={!form ? undefined : form.country.message}
    />
  </InputSection>
  <button class="btn btn-primary mx-auto">{buttonName}</button>
</form>
