<script>
  import MaintenanceAddView from '$lib/inventory/maintenance/MaintenanceAddView.svelte'

  export let form
</script>

<div class="p-8">
  <h1 class="text-4xl font-bold text-black mb-8">Add Maintenance</h1>
  <MaintenanceAddView {form} />
</div>
