<script>
  import PartAddView from '$lib/inventory/parts/PartAddView.svelte'

  export let form
</script>

<div class="p-8">
  <h1 class="text-4xl font-bold text-black mb-8">Add Part</h1>
  <PartAddView {form} />
</div>
