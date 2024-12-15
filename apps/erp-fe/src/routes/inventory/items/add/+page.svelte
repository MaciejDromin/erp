<script>
  import ItemAddView from '$lib/inventory/items/ItemAddView.svelte'

  export let form
</script>

<div class="p-8">
  <h1 class="text-4xl font-bold text-black mb-8">Add Item</h1>
  <ItemAddView form={form} />
</div>
