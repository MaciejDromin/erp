<script>
  import { MoneyOperationType } from '$lib/finances/types/financialTypes'

  export let data = undefined

  let categoryId = data === undefined ? undefined : data.category.uuid
  let name = data === undefined ? undefined : data.category.operationName
  let type = data === undefined ? undefined : data.category.operationType
  let buttonName = data === undefined ? 'Add' : 'Edit'
</script>

<form method="POST" class="mx-auto flex flex-col gap-3 py-6">
  <input
    name="categoryId"
    type="text"
    class="hidden"
    bind:value={categoryId}
  />
  <div class="flex flex-row gap-3 mx-auto">
    <select name="operationType" bind:value={type} class="select select-primary w-full max-w-xs">
      {#each Object.values(MoneyOperationType) as operationType}
        <option value={operationType}>{operationType}</option>
      {/each}
    </select>
    <input
      name="operationName"
      type="text"
      bind:value={name}
      placeholder="Operation Name"
      class="input input-bordered input-primary w-full max-w-xs"
    />
  </div>
  <button class="btn btn-primary mx-auto">{buttonName}</button>
</form>
