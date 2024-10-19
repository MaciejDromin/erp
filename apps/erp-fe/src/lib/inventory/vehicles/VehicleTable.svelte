<script lang="ts">
  import { onDestroy, onMount } from 'svelte'
  import { genericStore } from '$lib/stores/genericStore.ts'

  export let data: any = undefined
  let selectedVehicles: Map<string, string> = new Map()

  onMount(() => {
    if (
      $genericStore.inventory !== undefined &&
      $genericStore.inventory.properties !== undefined
    ) {
      $genericStore.inventory.properties.forEach((veh) =>
        selectedProperties.set(veh.id, veh)
      )
    }
  })

  onDestroy(() => {
    $genericStore.inventory = {}
    $genericStore.inventory.vehicles = Array.from(selectedVehicles.values())
  })

  const updateVehiclesList = (vehicle: any) => {
    if (selectedVehicles.has(vehicle.id)) {
      selectedVehicles.delete(vehicle.id)
    } else {
      selectedVehicles.set(vehicle.id, vehicle)
    }
    selectedVehicles = selectedVehicles
    $genericStore.inventory = {}
    $genericStore.inventory.vehicles = Array.from(selectedVehicles.values())
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
        <th>Registration Plate</th>
      </tr>
    </thead>
    <tbody>
      {#if data !== undefined}
        {#each data.content as vehicle}
          <tr
            class={`hover:bg-indigo-400 hover:text-black even:text-white hover:even:text-black hover:even:bg-indigo-400 cursor-pointer ease-in transition-all duration-200
                ${vehicleSelectedStyles(selectedVehicles, vehicle.id)}
                ${determineEvenBgColor(selectedVehicles, vehicle.id)}`}
            on:click={() => updateVehiclesList(vehicle)}
          >
            <td>{vehicle.id}</td>
            <td>{vehicle.name}</td>
            <td>{vehicle.year}</td>
            <td>{vehicle.make}</td>
            <td>{vehicle.model}</td>
            <td>{vehicle.odometer}</td>
            <td>{vehicle.registrationPlate}</td>
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
