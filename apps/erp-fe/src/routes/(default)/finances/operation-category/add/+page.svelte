<script>
  import OperationCategoryAddView from '$lib/finances/operation-category/OperationCategoryAddView.svelte'

  export let form
</script>

<div class="p-8">
  <h1 class="text-4xl font-bold text-black mb-8">Add Operation Category</h1>
  <OperationCategoryAddView {form} />
</div>
