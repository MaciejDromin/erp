<script lang="ts">
  import VehicleTable from '$lib/inventory/vehicles/VehicleTable.svelte'
  import Pageable from '$lib/Pageable.svelte'
  import {
    BodyStyle,
    DriveTrain,
    FuelType,
    Make,
    Transmission,
  } from '$lib/inventory/types/inventoryTypes'
</script>

<div id="vehicle" class="flex flex-col gap-3 px-10 pt-10">
  <form method="POST" class="mx-auto flex flex-col gap-3 py-6">
    <div class="flex flex-row gap-3">
      <input
        name="name"
        type="text"
        placeholder="Name"
        class="input input-bordered input-primary w-full max-w-xs"
      />
      <input
        name="year"
        type="text"
        placeholder="Year"
        class="input input-bordered input-primary w-full max-w-xs"
      />
      <input
        name="odometer"
        type="text"
        placeholder="Odometer"
        class="input input-bordered input-primary w-full max-w-xs"
      />
      <select name="bodyStyle" class="select select-primary w-full max-w-xs">
        {#each Object.values(BodyStyle) as bodyStyle}
          <option value={bodyStyle}>{bodyStyle}</option>
        {/each}
      </select>
    </div>
    <div class="flex flex-row gap-3">
      <select name="make" class="select select-primary w-full max-w-xs">
        {#each Object.values(Make) as make}
          <option value={make}>{make}</option>
        {/each}
      </select>
      <input
        name="model"
        type="text"
        placeholder="Model"
        class="input input-bordered input-primary w-full max-w-xs"
      />
      <select name="fuelType" class="select select-primary w-full max-w-xs">
        {#each Object.values(FuelType) as fuelType}
          <option value={fuelType}>{fuelType}</option>
        {/each}
      </select>
      <select name="driveTrain" class="select select-primary w-full max-w-xs">
        {#each Object.values(DriveTrain) as driveTrain}
          <option value={driveTrain}>{driveTrain}</option>
        {/each}
      </select>
    </div>
    <div class="flex flex-row gap-3">
      <select name="transmission" class="select select-primary w-full max-w-xs">
        {#each Object.values(Transmission) as transmission}
          <option value={transmission}>{transmission}</option>
        {/each}
      </select>
      <input
        name="engineType"
        type="text"
        placeholder="Engine Type"
        class="input input-bordered input-primary w-full max-w-xs"
      />
      <input
        name="vin"
        type="text"
        placeholder="VIN"
        class="input input-bordered input-primary w-full max-w-xs"
      />
      <input
        name="registrationPlate"
        type="text"
        placeholder="Registration Plate"
        class="input input-bordered input-primary w-full max-w-xs"
      />
      <button class="btn btn-primary">Add Row</button>
    </div>
  </form>

  <Pageable endpoint="/inventory/vehicles" component={VehicleTable} />
</div>
