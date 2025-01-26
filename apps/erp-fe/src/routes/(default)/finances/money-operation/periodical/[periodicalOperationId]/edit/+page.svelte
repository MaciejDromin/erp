<script lang="ts">
  import PeriodicalMoneyOperationAddView from '$lib/finances/money-operation/periodical/PeriodicalMoneyOperationAddView.svelte'
  import type { PageData } from './$types'

  export let data: PageData
  export let form
</script>

<div class="p-8">
  <h1 class="text-4xl font-bold text-black mb-8">
    Edit Periodical Money Operation
  </h1>
  <PeriodicalMoneyOperationAddView {data} {form} />
</div>
