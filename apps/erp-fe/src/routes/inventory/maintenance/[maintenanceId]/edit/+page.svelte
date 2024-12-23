<script lang="ts">
  import MaintenanceAddView from '$lib/inventory/maintenance/MaintenanceAddView.svelte'
  import type { PageData } from './$types'

  export let data: PageData
  export let form
</script>

<div class="p-8">
  <h1 class="text-4xl font-bold text-black mb-8">Edit Maintenance</h1>
  <MaintenanceAddView {data} {form} />
</div>
