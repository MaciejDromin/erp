<script lang="ts">
  import Modal from '$lib/Modal.svelte'
  import Pageable from '$lib/Pageable.svelte'
  import { genericStore } from '$lib/stores/genericStore.ts'
  import { onMount } from 'svelte'
  import TextInput from '$lib/commons/TextInput.svelte'
  import InputSection from '$lib/commons/InputSection.svelte'
  import { extractValue } from '$lib/scripts/dataExtractor.ts'
  import { idObjWrapper } from '$lib/scripts/valueWrappers.ts'
  import objectValuesKeys from '$lib/finances/types/objectValuesKeys.ts'

  export let config
  export let form

  let data = config.data

  let objectValueId = extractValue(data, form, objectValuesKeys.id)
  let obj = extractValue(
    data,
    form,
    objectValuesKeys.object,
    null,
    idObjWrapper
  )
  let objects: any[] = obj === null ? [] : [obj]
  let selectedObject = JSON.stringify(obj)
  let amount = extractValue(data, form, objectValuesKeys.amount)
  let currency = extractValue(data, form, objectValuesKeys.currencyCode)
  let buttonName = data.objectValue === undefined ? 'Add' : 'Edit'

  onMount(() => {
    $genericStore = {}
  })

  genericStore.subscribe((objectz) => {
    if (
      objectz.inventory !== undefined &&
      objectz.inventory[config.storeKey] !== undefined &&
      typeof objectz.inventory[config.storeKey][Symbol.iterator] === 'function'
    ) {
      objects = [...objectz.inventory[config.storeKey]]
      objects = objects
      selectedObject = JSON.stringify(objects[0])
    }
  })

  const determineButtonName = (arr: any[]): string => {
    if (arr.length === 0) return `select ${config.buttonName}`
    return `${arr.length} ${config.buttonName} selected`
  }
</script>

<form method="POST" class="mx-auto flex flex-col gap-3 py-6">
  <input
    name="objectValueId"
    type="text"
    class="hidden"
    bind:value={objectValueId}
  />
  <div class="flex flex-row gap-3">
    <div class="mx-auto">
      <InputSection name="Object Value" classes=" flex-row gap-2">
        <TextInput
          name="amount"
          bind:value={amount}
          placeholder="Amount"
          classes=" bg-white text-black"
          error={!form ? undefined : form.amount.message}
        />
        <TextInput
          name="currencyCode"
          bind:value={currency}
          placeholder="Currency Code"
          classes=" bg-white text-black"
          error={!form ? undefined : form.currencyCode.message}
        />
      </InputSection>
      <InputSection name="Item" classes=" flex-row gap-2">
        <select
          multiple
          name="object"
          class="p-4 mr-auto hidden"
          bind:value={selectedObject}
        >
          {#each objects as object}
            <option value={JSON.stringify(object)} />
          {/each}
        </select>
        <div class="mx-auto">
          <Modal
            modalId="object_modal"
            buttonName={determineButtonName(objects)}
          >
            <Pageable
              endpoint={config.endpoint}
              component={config.component}
              additionalSearch={config.data.objectIds.length === 0
                ? ''
                : `&objectIds=${config.data.objectIds.join(',')}`}
            />
            <button
              slot="button"
              class={`btn ${form && form.object.message ? 'btn-error-red' : 'btn-primary'}`}
              >{determineButtonName(objects)}</button
            >
          </Modal>
        </div>
      </InputSection>
    </div>
  </div>
  <button class="btn btn-primary mx-auto">{buttonName}</button>
</form>
