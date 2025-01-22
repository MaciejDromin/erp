<script>
  import CategoryAddView from '$lib/inventory/categories/CategoryAddView.svelte'

  export let form
</script>

<div class="p-8">
  <h1 class="text-4xl font-bold text-black mb-8">Add Category</h1>
  <CategoryAddView {form} />
</div>
