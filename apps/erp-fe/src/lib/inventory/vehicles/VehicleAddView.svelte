<script lang="ts">
  import {
    BodyStyle,
    DriveTrain,
    FuelType,
    Make,
    Transmission,
  } from '$lib/inventory/types/inventoryTypes'
  import TextInput from '$lib/commons/TextInput.svelte'
  import SelectInput from '$lib/commons/SelectInput.svelte'
  import InputSection from '$lib/commons/InputSection.svelte'

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
  <div class="flex flex-row gap-3 mx-auto">
    <InputSection name="Details" classes=" flex-row gap-2">
      <TextInput
        name="name"
        bind:value={name}
        placeholder="Name"
        classes=" bg-white text-black"
      />
      <TextInput
        name="odometer"
        bind:value={odometer}
        placeholder="Odometer"
        classes=" bg-white text-black"
      />
    </InputSection>
    <InputSection name="Identification" classes=" flex-row gap-2">
      <TextInput
        name="vin"
        bind:value={vin}
        placeholder="VIN"
        classes=" bg-white text-black"
      />
      <TextInput
        name="registrationPlate"
        bind:value={registrationPlate}
        placeholder="Registration Plate"
        classes=" bg-white text-black"
      />
    </InputSection>
  </div>
  <InputSection name="Specification" classes=" flex-col gap-2 w-fit mx-auto">
    <div class="flex flex-row gap-3">
      <SelectInput
        name="make"
        bind:value={make}
        classes=" bg-white text-black"
        options={Object.values(Make)}
      />
      <TextInput
        name="model"
        bind:value={model}
        placeholder="Model"
        classes=" bg-white text-black"
      />
      <SelectInput
        name="bodyStyle"
        bind:value={bodyStyle}
        classes=" bg-white text-black"
        options={Object.values(BodyStyle)}
      />
      <TextInput
        name="year"
        bind:value={year}
        placeholder="Year"
        classes=" bg-white text-black"
      />
    </div>
    <div class="flex flex-row gap-3">
      <SelectInput
        name="driveTrain"
        bind:value={driveTrain}
        classes=" bg-white text-black"
        options={Object.values(DriveTrain)}
      />
      <SelectInput
        name="transmission"
        bind:value={transmission}
        classes=" bg-white text-black"
        options={Object.values(Transmission)}
      />
      <SelectInput
        name="fuelType"
        bind:value={fuelType}
        classes=" bg-white text-black"
        options={Object.values(FuelType)}
      />
      <TextInput
        name="engineType"
        bind:value={engineType}
        placeholder="Engine Type"
        classes=" bg-white text-black"
      />
    </div>
  </InputSection>
  <button class="btn btn-primary mx-auto">{buttonName}</button>
</form>
