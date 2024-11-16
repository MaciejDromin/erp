<script lang="ts">
  import CategoryAddView from '$lib/inventory/categories/CategoryAddView.svelte'
  import type { PageData } from './$types'

  export let data: PageData
</script>

<div class="p-8">
  <h1 class="text-4xl font-bold text-black mb-8">Edit Category</h1>
  <CategoryAddView {data} />
</div>
