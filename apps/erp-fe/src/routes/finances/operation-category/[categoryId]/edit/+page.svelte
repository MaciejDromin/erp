<script lang="ts">
  import OperationCategoryAddView from '$lib/finances/operation-category/OperationCategoryAddView.svelte'
  import type { PageData } from './$types'

  export let data: PageData
  export let form
</script>

<div class="p-8">
  <h1 class="text-4xl font-bold text-black mb-8">Edit Operation Category</h1>
  <OperationCategoryAddView {data} {form} />
</div>
