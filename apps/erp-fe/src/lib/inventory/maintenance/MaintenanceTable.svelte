<script lang="ts">
  import { onDestroy, onMount } from 'svelte'
  import { maintenanceStore } from '../stores/selectedMaintenance'

  export let data: any = undefined
  let selectedMaintenanceIds: Map<string, string> = new Map()

  onMount(() => {
    $maintenanceStore.forEach((maint) =>
      selectedMaintenanceIds.set(maint, 'ok')
    )
  })

  onDestroy(() => {
    $maintenanceStore = Array.from(selectedMaintenanceIds.keys())
  })

  const updateMaintenanceList = (maintenanceId: string) => {
    if (selectedMaintenanceIds.has(maintenanceId)) {
      selectedMaintenanceIds.delete(maintenanceId)
    } else {
      selectedMaintenanceIds.set(maintenanceId, 'ok')
    }
    selectedMaintenanceIds = selectedMaintenanceIds
  }

  const maintenanceSelectedStyles = (
    maintenanceMap: Map<string, string>,
    maintenanceId: string
  ): string => {
    if (!maintenanceMap.has(maintenanceId)) return ''
    return 'bg-indigo-600 text-white'
  }

  const determineEvenBgColor = (
    maintenanceMap: Map<string, string>,
    maintenanceId: string
  ): string => {
    if (!maintenanceMap.has(maintenanceId)) return 'even:bg-black'
    return 'even:bg-indigo-600'
  }
</script>

<div class="overflow-x-auto text-primary-content mx-auto">
  <!-- <table class="table table-zebra"> -->
  <table class="table">
    <!-- head -->
    <thead class="text-primary-content">
      <tr>
        <th>ID</th>
        <th>Date</th>
        <th>Odometer</th>
      </tr>
    </thead>
    <tbody>
      {#if data !== undefined}
        {#each data.content as maintenance}
          <tr
            class={`hover:bg-indigo-400 hover:text-black even:text-white hover:even:text-black hover:even:bg-indigo-400 cursor-pointer ease-in transition-all duration-200
                ${maintenanceSelectedStyles(selectedMaintenanceIds, maintenance.id)}
                ${determineEvenBgColor(selectedMaintenanceIds, maintenance.id)}`}
            on:click={() => updateMaintenanceList(maintenance.id)}
          >
            <td>{maintenance.id}</td>
            <td>{maintenance.date}</td>
            <td>{maintenance.odometer}</td>
          </tr>
        {/each}
      {/if}
    </tbody>
  </table>
</div>

<!-- <style>
    .table-zebra tbody tr:nth-child(even) {
        color: white;
    }
</style> -->
