<script lang="ts">
  import TextInput from '$lib/commons/TextInput.svelte'
  import InputSection from '$lib/commons/InputSection.svelte'
  import { extractValue } from '$lib/scripts/dataExtractor.ts'
  import contractorKeys from '$lib/inventory/types/contractorKeys.ts'

  export let data = undefined
  export let form

  let contractorId = extractValue(data, form, contractorKeys.id)
  let name = extractValue(data, form, contractorKeys.name)
  let phoneNumber = extractValue(data, form, contractorKeys.phoneNumber)
  let email = extractValue(data, form, contractorKeys.email)
  let website = extractValue(data, form, contractorKeys.website)
  let buttonName = data === undefined ? 'Add' : 'Edit'
</script>

<form method="POST" class="mx-auto flex flex-col gap-3 py-6">
  <input
    name="contractorId"
    type="text"
    class="hidden"
    bind:value={contractorId}
  />
  <InputSection name="Details" classes=" flex-row gap-2 w-fit mx-auto">
    <TextInput
      name="name"
      bind:value={name}
      placeholder="Name"
      classes=" bg-white text-black"
      error={!form ? undefined : form.name.message}
    />
    <TextInput
      name="website"
      bind:value={website}
      placeholder="Website"
      classes=" bg-white text-black"
      error={!form ? undefined : form.website.message}
    />
  </InputSection>
  <InputSection
    name="Contact Information"
    classes=" flex-row gap-2 w-fit mx-auto"
  >
    <TextInput
      name="phoneNumber"
      bind:value={phoneNumber}
      placeholder="Phone Number"
      classes=" bg-white text-black"
      error={!form ? undefined : form.phoneNumber.message}
    />
    <TextInput
      name="email"
      bind:value={email}
      placeholder="Email"
      classes=" bg-white text-black"
      error={!form ? undefined : form.email.message}
    />
  </InputSection>
  <button class="btn btn-primary mx-auto">{buttonName}</button>
</form>
