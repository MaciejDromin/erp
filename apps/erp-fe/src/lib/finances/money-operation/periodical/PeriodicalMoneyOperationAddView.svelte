<script lang="ts">
  import { genericStore } from '$lib/stores/genericStore.ts'
  import { onMount } from 'svelte'
  import { MoneyOperationType, Month } from '$lib/finances/types/financialTypes'
  import Pageable from '$lib/Pageable.svelte'
  import Modal from '$lib/Modal.svelte'
  import OperationCategoryTable from '$lib/finances/operation-category/OperationCategoryTable.svelte'
  import TextInput from '$lib/commons/TextInput.svelte'
  import TextArea from '$lib/commons/TextArea.svelte'
  import SelectInput from '$lib/commons/SelectInput.svelte'
  import InputSection from '$lib/commons/InputSection.svelte'

  export let data = undefined

  let periodicalOperationId = data === undefined ? undefined : data.operation.id
  let category =
    data === undefined ? undefined : { id: data.operation.operationCategory.id }
  let categories: any[] = category === undefined ? [] : [category]
  let selectedCategory =
    category === undefined ? undefined : JSON.stringify(category)
  let amount = data === undefined ? undefined : data.operation.amount.value
  let currencyCode =
    data === undefined ? undefined : data.operation.amount.currencyCode
  let operationDescription =
    data === undefined ? undefined : data.operation.operationDescription
  let operationType =
    data === undefined
      ? MoneyOperationType.INCOME
      : data.operation.operationType
  let repetitionPeriod =
    data === undefined ? undefined : data.operation.repetitionPeriod
  let nextApplicableMonth =
    data === undefined ? undefined : data.operation.nextApplicableMonth
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
    name="periodicalOperationId"
    type="text"
    class="hidden"
    bind:value={periodicalOperationId}
  />
  <div class="flex flex-row gap-3">
    <div>
      <InputSection name="Operation" classes=" flex-row gap-2">
        <TextInput
          name="amount"
          bind:value={amount}
          placeholder="Amount"
          classes=" bg-white text-black"
        />
        <TextInput
          name="currencyCode"
          bind:value={currencyCode}
          placeholder="Currency Code"
          classes=" bg-white text-black"
        />
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
            <button slot="button" class="btn btn-primary"
              >{determineButtonName(categories)}</button
            >
          </Modal>
        </div>
      </InputSection>
    </div>
    <div class="grow flex flex-col">
      <InputSection name="Schedule" classes=" flex-row gap-2">
        <SelectInput
          name="nextApplicableMonth"
          bind:value={nextApplicableMonth}
          classes=" bg-white text-black"
          options={Object.values(Month)}
        />
        <select
          name="nextApplicableMonth"
          class="p-4 mr-auto hidden"
          bind:value={nextApplicableMonth}
        >
          {#each Object.values(Month) as month}
            <option value={month}></option>
          {/each}
        </select>
        <TextInput
          name="repetitionPeriod"
          bind:value={repetitionPeriod}
          placeholder="Repetition Period"
          classes=" bg-white text-black"
        />
      </InputSection>
      <div>
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
  </div>
  <button class="btn btn-primary mx-auto">{buttonName}</button>
</form>
