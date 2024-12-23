<script>
  import { MoneyOperationType } from '$lib/finances/types/financialTypes'
  import TextInput from '$lib/commons/TextInput.svelte'
  import SelectInput from '$lib/commons/SelectInput.svelte'
  import InputSection from '$lib/commons/InputSection.svelte'
  import { extractValue } from '$lib/scripts/dataExtractor.ts'
  import categoryKeys from '$lib/finances/types/categoryKeys.ts'

  export let data = undefined
  export let form

  let categoryId = extractValue(data, form, categoryKeys.id)
  let name = extractValue(data, form, categoryKeys.name)
  let type = extractValue(data, form, categoryKeys.type)
  let buttonName = data === undefined ? 'Add' : 'Edit'
</script>

<form method="POST" class="mx-auto flex flex-col gap-3">
  <input name="categoryId" type="text" class="hidden" bind:value={categoryId} />
  <div class="flex flex-row gap-3 mx-auto">
    <div class="mx-auto">
      <InputSection name="Object Value" classes=" flex-row gap-2">
        <TextInput
          name="operationName"
          bind:value={name}
          placeholder="Operation Name"
          classes=" bg-white text-black"
          error={!form ? undefined : form.operationName.message}
        />
        <SelectInput
          name="operationType"
          bind:value={type}
          classes=" bg-white text-black"
          options={Object.values(MoneyOperationType)}
        />
      </InputSection>
    </div>
  </div>
  <button class="btn btn-primary mx-auto">{buttonName}</button>
</form>
