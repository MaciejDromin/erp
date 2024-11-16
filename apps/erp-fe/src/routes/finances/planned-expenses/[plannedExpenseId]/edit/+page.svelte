<script lang="ts">
  import PlannedExpensesAddView from '$lib/finances/planned-expenses/PlannedExpensesAddView.svelte'
  import type { PageData } from './$types'

  export let data: PageData
</script>

<div class="p-8">
  <h1 class="text-4xl font-bold text-black mb-8">Edit Planned Expense</h1>
  <PlannedExpensesAddView {data} />
</div>
