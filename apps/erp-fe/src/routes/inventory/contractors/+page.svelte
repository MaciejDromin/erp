<script lang="ts">
  import ContractorsTable from '$lib/inventory/contractors/ContractorsTable.svelte'
  import Pageable from '$lib/Pageable.svelte'
</script>

<div id="contractor" class="flex flex-col gap-3 px-10 pt-10">
  <form method="POST" class="mx-auto flex flex-col gap-3 py-6">
    <div class="flex flex-row gap-3">
      <input
        name="name"
        type="text"
        placeholder="Name"
        class="input input-bordered input-primary w-full max-w-xs"
      />
      <input
        name="phoneNumber"
        type="text"
        placeholder="Phone Number"
        class="input input-bordered input-primary w-full max-w-xs"
      />
      <input
        name="email"
        type="text"
        placeholder="Email"
        class="input input-bordered input-primary w-full max-w-xs"
      />
      <input
        name="website"
        type="text"
        placeholder="Website"
        class="input input-bordered input-primary w-full max-w-xs"
      />
      <button class="btn btn-primary">Add Row</button>
    </div>
  </form>

  <Pageable endpoint="/inventory/contractors" component={ContractorsTable} />
</div>
