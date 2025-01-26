<script lang="ts">
  import MoneyOperationAddView from '$lib/finances/money-operation/MoneyOperationAddView.svelte'
  import type { PageData } from './$types'

  export let data: PageData
  export let form
</script>

<div class="p-8">
  <h1 class="text-4xl font-bold text-black mb-8">Edit Money Operation</h1>
  <MoneyOperationAddView {data} {form} />
</div>
