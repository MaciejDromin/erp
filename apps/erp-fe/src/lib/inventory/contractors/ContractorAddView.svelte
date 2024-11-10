<script lang="ts">
  export let data = undefined

  let contractorId = data === undefined ? undefined : data.contractor.id
  let name = data === undefined ? undefined : data.contractor.name
  let phoneNumber = data === undefined ? undefined : data.contractor.contactInformation.phoneNumber
  let email = data === undefined ? undefined : data.contractor.contactInformation.email
  let website = data === undefined ? undefined : data.contractor.contactInformation.website
  let buttonName = data === undefined ? 'Add' : 'Edit'
</script>
<form method="POST" class="mx-auto flex flex-col gap-3 py-6">
  <input name="contractorId" type="text" class="hidden" bind:value={contractorId} />
  <div class="flex flex-row gap-3">
    <input
      name="name"
      bind:value={name}
      type="text"
      placeholder="Name"
      class="input input-bordered input-primary w-full max-w-xs"
    />
    <input
      name="phoneNumber"
      bind:value={phoneNumber}
      type="text"
      placeholder="Phone Number"
      class="input input-bordered input-primary w-full max-w-xs"
    />
    <input
      name="email"
      bind:value={email}
      type="text"
      placeholder="Email"
      class="input input-bordered input-primary w-full max-w-xs"
    />
    <input
      name="website"
      bind:value={website}
      type="text"
      placeholder="Website"
      class="input input-bordered input-primary w-full max-w-xs"
    />
    <button class="btn btn-primary">{buttonName} Row</button>
  </div>
</form>
