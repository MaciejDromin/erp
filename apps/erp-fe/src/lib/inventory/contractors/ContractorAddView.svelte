<script lang="ts">
  import TextInput from '$lib/commons/TextInput.svelte'
  import InputSection from '$lib/commons/InputSection.svelte'

  export let data = undefined

  let contractorId = data === undefined ? undefined : data.contractor.id
  let name = data === undefined ? undefined : data.contractor.name
  let phoneNumber =
    data === undefined
      ? undefined
      : data.contractor.contactInformation.phoneNumber
  let email =
    data === undefined ? undefined : data.contractor.contactInformation.email
  let website =
    data === undefined ? undefined : data.contractor.contactInformation.website
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
    />
    <TextInput
      name="website"
      bind:value={website}
      placeholder="Website"
      classes=" bg-white text-black"
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
    />
    <TextInput
      name="email"
      bind:value={email}
      placeholder="Email"
      classes=" bg-white text-black"
    />
  </InputSection>
  <button class="btn btn-primary mx-auto">{buttonName}</button>
</form>
