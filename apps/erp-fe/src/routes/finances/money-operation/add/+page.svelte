<script>
  import MoneyOperationAddView from '$lib/finances/money-operation/MoneyOperationAddView.svelte'

  export let form
</script>

<div class="p-8">
  <h1 class="text-4xl font-bold text-black mb-8">Add Money Operation</h1>
  <MoneyOperationAddView {form} />
</div>
