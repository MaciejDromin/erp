<script lang="ts">
  import VehicleTable from '$lib/inventory/vehicles/VehicleTable.svelte'
  import Modal from '$lib/Modal.svelte'
  import Pageable from '$lib/Pageable.svelte'
  import { vehiclesStore } from '$lib/inventory/stores/selectedVehicles'
  import { onMount } from 'svelte'
  import ObjectValueTable from '$lib/finances/object-value/ObjectValueTable.svelte'
  import type { PageData } from './$types'
  import { ObjectType } from '$lib/finances/types/financialTypes'

  export let data: PageData

  let vehicles: any[] = []

  onMount(() => {
    $vehiclesStore = []
  })

  vehiclesStore.subscribe((veh) => {
    vehicles = [...veh]
    vehicles = vehicles
  })

  const determineButtonName = (arr: any[]): string => {
    if (arr.length === 0) return 'select vehicle'
    return `${arr.length} vehicle selected`
  }
</script>

<div id="object-value" class="flex flex-col gap-3 px-10 pt-10">
  <div class="stats shadow mx-auto">
    <div class="stat">
      <div class="stat-figure text-primary text-4xl">$</div>
      <div class="stat-title">Total Amount</div>
      <div class="stat-value text-primary">
        {data.objectsValue.totalAmount.value}
        {data.objectsValue.totalAmount.currencyCode}
      </div>
      <div class="stat-desc">Total amount of all objects</div>
    </div>

    <div class="stat">
      <div class="stat-figure text-secondary text-3xl">0..*</div>
      <div class="stat-title">Objects Count</div>
      <div class="stat-value text-secondary">
        {data.objectsValue.objectsCount}
      </div>
      <div class="stat-desc">How much objects were counted</div>
    </div>
  </div>
  <form method="POST" class="mx-auto flex flex-col gap-3 py-6">
    <div class="flex flex-row gap-3">
      <input
        name="amount"
        type="text"
        placeholder="Amount"
        class="input input-bordered input-primary w-full max-w-xs"
      />
      <input
        name="currencyCode"
        type="text"
        placeholder="Currency Code"
        class="input input-bordered input-primary w-full max-w-xs"
      />
    </div>

    <div class="flex flex-row gap-3">
      <select
        multiple
        name="vehicleId"
        class="p-4 mr-auto hidden"
        bind:value={vehicles}
      >
        {#each vehicles as vehicle}
          <option value={vehicle} />
        {/each}
      </select>
      <div class="mr-auto">
        <Modal
          modalId="vehicle_modal"
          buttonName={determineButtonName(vehicles)}
        >
          <Pageable
            endpoint="/inventory/vehicles"
            component={VehicleTable}
            additionalSearch={data.objectIds.length === 0
              ? ''
              : `&objectIds=${data.objectIds.join(',')}`}
          />
        </Modal>
      </div>
      <button class="btn btn-primary">Add Row</button>
    </div>
  </form>

  <Pageable
    endpoint="/finances/object-value"
    additionalSearch={`&objectType=${ObjectType.VEHICLE}`}
    additionalParams={{objectType: ObjectType.VEHICLE}}
    component={ObjectValueTable}
  />
</div>
