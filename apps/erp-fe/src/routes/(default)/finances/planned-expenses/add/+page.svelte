<script>
  import PlannedExpensesAddView from '$lib/finances/planned-expenses/PlannedExpensesAddView.svelte'

  export let form
</script>

<div class="p-8">
  <h1 class="text-4xl font-bold text-black mb-8">Add Planned Expense</h1>
  <PlannedExpensesAddView {form} />
</div>
