<script>
  import PropertyAddView from '$lib/inventory/properties/PropertyAddView.svelte'

  export let form
</script>

<div class="p-8">
  <h1 class="text-4xl font-bold text-black mb-8">Add Property</h1>
  <PropertyAddView {form} />
</div>
