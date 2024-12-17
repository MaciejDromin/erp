<script lang="ts">
  import { MoneyOperationType } from '$lib/finances/types/financialTypes'
  import { genericStore } from '$lib/stores/genericStore.ts'
  import { onMount } from 'svelte'
  import Modal from '$lib/Modal.svelte'
  import OperationCategoryTable from '$lib/finances/operation-category/OperationCategoryTable.svelte'
  import Pageable from '$lib/Pageable.svelte'
  import TextInput from '$lib/commons/TextInput.svelte'
  import TextArea from '$lib/commons/TextArea.svelte'
  import SelectInput from '$lib/commons/SelectInput.svelte'
  import InputSection from '$lib/commons/InputSection.svelte'
  import { extractValue } from '$lib/scripts/dataExtractor.ts'
  import moneyOperationKeys from '$lib/finances/types/moneyOperationKeys.ts'

  export let data = undefined
  export let form

  let operationId = extractValue(data, form, moneyOperationKeys.operationId)

  let category = extractValue(data, form, moneyOperationKeys.category, null)
  let categories: any[] = category === null ? [] : [category]
  let selectedCategory = JSON.stringify(category)
  let amount = extractValue(data, form, moneyOperationKeys.amount)
  let currencyCode = extractValue(data, form, moneyOperationKeys.currencyCode)
  let operationDescription = extractValue(
    data,
    form,
    moneyOperationKeys.operationDescription
  )
  let operationType = extractValue(
    data,
    form,
    moneyOperationKeys.operationType,
    MoneyOperationType.INCOME
  )
  let effectiveDate = extractValue(data, form, moneyOperationKeys.effectiveDate)
  let buttonName = data === undefined ? 'Add' : 'Edit'

  onMount(() => {
    $genericStore = {}
  })

  genericStore.subscribe((catgz) => {
    if (
      catgz.finances !== undefined &&
      catgz.finances.categories !== undefined &&
      typeof catgz.finances.categories[Symbol.iterator] === 'function'
    ) {
      categories = [...catgz.finances.categories]
      categories = categories
      selectedCategory = JSON.stringify(categories[0])
    }
  })

  const determineButtonName = (arr: any[]): string => {
    if (arr.length === 0) return 'select category'
    return `${arr.length} category selected`
  }
</script>

<form method="POST" class="mx-auto flex flex-col gap-3">
  <input
    name="operationId"
    type="text"
    class="hidden"
    bind:value={operationId}
  />
  <div class="flex flex-row gap-3">
    <div>
      <InputSection name="Operation" classes=" flex-row gap-2">
        <TextInput
          name="amount"
          bind:value={amount}
          placeholder="Amount"
          classes=" bg-white text-black"
          error={!form ? undefined : form.amount.value.message}
        />
        <TextInput
          name="currencyCode"
          bind:value={currencyCode}
          placeholder="Currency Code"
          classes=" bg-white text-black"
          error={!form ? undefined : form.amount.currencyCode.message}
        />
        {#if data !== undefined}
          <TextInput
            name="effectiveDate"
            bind:value={effectiveDate}
            placeholder="Effective Date"
            classes=" bg-white text-black"
            error={!form ? undefined : form.effectiveDate.message}
          />
        {/if}
      </InputSection>
      <InputSection name="Category" classes=" flex-row gap-2 items-center">
        <SelectInput
          name="operationType"
          bind:value={operationType}
          classes=" bg-white text-black"
          options={Object.values(MoneyOperationType)}
        />
        <select
          multiple
          name="category"
          class="p-4 mr-auto hidden"
          bind:value={selectedCategory}
        >
          {#each categories as category}
            <option value={JSON.stringify(category)}></option>
          {/each}
        </select>
        <div class="mr-auto">
          <Modal
            modalId="category_modal"
            buttonName={determineButtonName(categories)}
          >
            <Pageable
              endpoint="/finances/operation-category"
              component={OperationCategoryTable}
              additionalSearch={`&operationType=${operationType}`}
            />
            <button
              slot="button"
              class={`btn ${form && form.category.message ? 'btn-error-red' : 'btn-primary'}`}
              >{determineButtonName(categories)}</button
            >
          </Modal>
        </div>
      </InputSection>
    </div>
    <div class="grow flex">
      <InputSection name="Description" classes=" h-full w-full min-h-full">
        <TextArea
          name="operationDescription"
          bind:value={operationDescription}
          placeholder="Operation Description"
          classes=" bg-white text-black w-full min-h-full"
        />
      </InputSection>
    </div>
  </div>
  <button class="btn btn-primary mx-auto">{buttonName}</button>
</form>
