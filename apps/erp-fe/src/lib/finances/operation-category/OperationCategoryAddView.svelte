<script>
  import { MoneyOperationType } from '$lib/finances/types/financialTypes'
</script>

<form method="POST" class="mx-auto flex flex-col gap-3 py-6">
  <div class="flex flex-row gap-3 mx-auto">
    <select name="operationType" class="select select-primary w-full max-w-xs">
      {#each Object.values(MoneyOperationType) as operationType}
        <option value={operationType}>{operationType}</option>
      {/each}
    </select>
    <input
      name="operationName"
      type="text"
      placeholder="Operation Name"
      class="input input-bordered input-primary w-full max-w-xs"
    />
  </div>
  <button class="btn btn-primary mx-auto">Add</button>
</form>
