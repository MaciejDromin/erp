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
  import { extractValue } from '$lib/scripts/dataExtractor.ts'
  import vehicleKeys from '$lib/inventory/types/vehicleKeys.ts'

  export let data = undefined
  export let form

  let vehicleId = extractValue(data, form, vehicleKeys.id)
  let name = extractValue(data, form, vehicleKeys.name)
  let year = extractValue(data, form, vehicleKeys.year)
  let odometer = extractValue(data, form, vehicleKeys.odometer)
  let bodyStyle = extractValue(data, form, vehicleKeys.bodyStyle)
  let make = extractValue(data, form, vehicleKeys.make)
  let model = extractValue(data, form, vehicleKeys.model)
  let fuelType = extractValue(data, form, vehicleKeys.fuelType)
  let driveTrain = extractValue(data, form, vehicleKeys.driveTrain)
  let transmission = extractValue(data, form, vehicleKeys.transmission)
  let engineType = extractValue(data, form, vehicleKeys.engineType)
  let vin = extractValue(data, form, vehicleKeys.vin)
  let registrationPlate = extractValue(
    data,
    form,
    vehicleKeys.registrationPlate
  )
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
        error={!form ? undefined : form.name.message}
      />
      <TextInput
        name="odometer"
        bind:value={odometer}
        placeholder="Odometer"
        classes=" bg-white text-black"
        error={!form ? undefined : form.odometer.message}
      />
    </InputSection>
    <InputSection name="Identification" classes=" flex-row gap-2">
      <TextInput
        name="vin"
        bind:value={vin}
        placeholder="VIN"
        classes=" bg-white text-black"
        error={!form ? undefined : form.vin.message}
      />
      <TextInput
        name="registrationPlate"
        bind:value={registrationPlate}
        placeholder="Registration Plate"
        classes=" bg-white text-black"
        error={!form ? undefined : form.registrationPlate.message}
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
        error={!form ? undefined : form.model.message}
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
        error={!form ? undefined : form.year.message}
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
        error={!form ? undefined : form.engineType.message}
      />
    </div>
  </InputSection>
  <button class="btn btn-primary mx-auto">{buttonName}</button>
</form>
