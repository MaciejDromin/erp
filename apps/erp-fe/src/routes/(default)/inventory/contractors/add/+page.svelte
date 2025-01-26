<script>
  import ContractorAddView from '$lib/inventory/contractors/ContractorAddView.svelte'

  export let form
</script>

<div class="p-8">
  <h1 class="text-4xl font-bold text-black mb-8">Add Contractor</h1>
  <ContractorAddView {form} />
</div>
