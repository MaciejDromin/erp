<script lang="ts">
  import {
    BodyStyle,
    DriveTrain,
    FuelType,
    Make,
    Transmission,
  } from '$lib/inventory/types/inventoryTypes'

  export let data = undefined

  let vehicleId = data === undefined ? undefined : data.vehicle.id
  let name = data === undefined ? undefined : data.vehicle.name
  let year = data === undefined ? undefined : data.vehicle.year
  let odometer = data === undefined ? undefined : data.vehicle.odometer
  let bodyStyle = data === undefined ? undefined : data.vehicle.bodyStyle
  let make = data === undefined ? undefined : data.vehicle.make
  let model = data === undefined ? undefined : data.vehicle.model
  let fuelType = data === undefined ? undefined : data.vehicle.fuelType
  let driveTrain = data === undefined ? undefined : data.vehicle.driveTrain
  let transmission = data === undefined ? undefined : data.vehicle.transmission
  let engineType = data === undefined ? undefined : data.vehicle.engineType
  let vin = data === undefined ? undefined : data.vehicle.vin
  let registrationPlate =
    data === undefined ? undefined : data.vehicle.registrationPlate
  let buttonName = data === undefined ? 'Add' : 'Edit'
</script>

<form method="POST" class="mx-auto flex flex-col gap-3 py-6">
  <input name="vehicleId" type="text" class="hidden" bind:value={vehicleId} />
  <div class="flex flex-row gap-3">
    <input
      name="name"
      bind:value={name}
      type="text"
      placeholder="Name"
      class="input input-bordered input-primary w-full max-w-xs"
    />
    <input
      name="year"
      bind:value={year}
      type="text"
      placeholder="Year"
      class="input input-bordered input-primary w-full max-w-xs"
    />
    <input
      name="odometer"
      bind:value={odometer}
      type="text"
      placeholder="Odometer"
      class="input input-bordered input-primary w-full max-w-xs"
    />
    <select
      name="bodyStyle"
      bind:value={bodyStyle}
      class="select select-primary w-full max-w-xs"
    >
      {#each Object.values(BodyStyle) as bodyStyle}
        <option value={bodyStyle}>{bodyStyle}</option>
      {/each}
    </select>
  </div>
  <div class="flex flex-row gap-3">
    <select
      name="make"
      bind:value={make}
      class="select select-primary w-full max-w-xs"
    >
      {#each Object.values(Make) as make}
        <option value={make}>{make}</option>
      {/each}
    </select>
    <input
      name="model"
      bind:value={model}
      type="text"
      placeholder="Model"
      class="input input-bordered input-primary w-full max-w-xs"
    />
    <select
      name="fuelType"
      bind:value={fuelType}
      class="select select-primary w-full max-w-xs"
    >
      {#each Object.values(FuelType) as fuelType}
        <option value={fuelType}>{fuelType}</option>
      {/each}
    </select>
    <select
      name="driveTrain"
      bind:value={driveTrain}
      class="select select-primary w-full max-w-xs"
    >
      {#each Object.values(DriveTrain) as driveTrain}
        <option value={driveTrain}>{driveTrain}</option>
      {/each}
    </select>
  </div>
  <div class="flex flex-row gap-3">
    <select
      name="transmission"
      bind:value={transmission}
      class="select select-primary w-full max-w-xs"
    >
      {#each Object.values(Transmission) as transmission}
        <option value={transmission}>{transmission}</option>
      {/each}
    </select>
    <input
      name="engineType"
      bind:value={engineType}
      type="text"
      placeholder="Engine Type"
      class="input input-bordered input-primary w-full max-w-xs"
    />
    <input
      name="vin"
      bind:value={vin}
      type="text"
      placeholder="VIN"
      class="input input-bordered input-primary w-full max-w-xs"
    />
    <input
      name="registrationPlate"
      bind:value={registrationPlate}
      type="text"
      placeholder="Registration Plate"
      class="input input-bordered input-primary w-full max-w-xs"
    />
    <button class="btn btn-primary">{buttonName} Row</button>
  </div>
</form>
