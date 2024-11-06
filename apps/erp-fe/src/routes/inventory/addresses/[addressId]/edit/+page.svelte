<script lang="ts">
  import AddressAddView from '$lib/inventory/addresses/AddressAddView.svelte'
  import type { PageData } from './$types'

  export let data: PageData
</script>

<div class="p-8">
  <h1 class="text-4xl font-bold text-black mb-8">Edit Address</h1>
  <AddressAddView {data} />
</div>
