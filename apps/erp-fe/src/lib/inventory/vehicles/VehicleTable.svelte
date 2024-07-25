<script lang="ts">
  import { onDestroy, onMount } from 'svelte'
  import { vehiclesStore } from '../stores/selectedVehicles'

  export let data: any = undefined
  let selectedVehiclesIds: Map<string, string> = new Map()

  onMount(() => {
    $vehiclesStore.forEach((veh) => selectedVehiclesIds.set(veh, 'ok'))
  })

  onDestroy(() => {
    $vehiclesStore = Array.from(selectedVehiclesIds.keys())
  })

  const updateVehiclesList = (vehicleId: string) => {
    if (selectedVehiclesIds.has(vehicleId)) {
      selectedVehiclesIds.delete(vehicleId)
    } else {
      selectedVehiclesIds.set(vehicleId, 'ok')
    }
    selectedVehiclesIds = selectedVehiclesIds
  }

  const vehicleSelectedStyles = (
    vehicleMap: Map<string, string>,
    vehicleId: string
  ): string => {
    if (!vehicleMap.has(vehicleId)) return ''
    return 'bg-indigo-600 text-white'
  }

  const determineEvenBgColor = (
    vehicleMap: Map<string, string>,
    vehicleId: string
  ): string => {
    if (!vehicleMap.has(vehicleId)) return 'even:bg-black'
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
        <th>Name</th>
        <th>Year</th>
        <th>Make</th>
        <th>Model</th>
        <th>Odometer</th>
      </tr>
    </thead>
    <tbody>
      {#if data !== undefined}
        {#each data.content as vehicle}
          <tr
            class={`hover:bg-indigo-400 hover:text-black even:text-white hover:even:text-black hover:even:bg-indigo-400 cursor-pointer ease-in transition-all duration-200
                ${vehicleSelectedStyles(selectedVehiclesIds, vehicle.id)}
                ${determineEvenBgColor(selectedVehiclesIds, vehicle.id)}`}
            on:click={() => updateVehiclesList(vehicle.id)}
          >
            <td>{vehicle.id}</td>
            <td>{vehicle.name}</td>
            <td>{vehicle.year}</td>
            <td>{vehicle.make}</td>
            <td>{vehicle.model}</td>
            <td>{vehicle.odometer}</td>
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
