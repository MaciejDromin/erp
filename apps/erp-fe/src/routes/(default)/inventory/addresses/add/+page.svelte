<script>
  import AddressAddView from '$lib/inventory/addresses/AddressAddView.svelte'

  export let form
</script>

<div class="p-8">
  <h1 class="text-4xl font-bold text-black mb-8">Add Address</h1>
  <AddressAddView {form} />
</div>
