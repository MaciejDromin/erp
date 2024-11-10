<script lang="ts">
  import ContractorAddView from '$lib/inventory/contractors/ContractorAddView.svelte'
  import type { PageData } from './$types'

  export let data: PageData
</script>

<div class="p-8">
  <h1 class="text-4xl font-bold text-black mb-8">Edit Contractor</h1>
  <ContractorAddView {data} />
</div>
