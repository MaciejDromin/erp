<script>
  import TextInput from '$lib/commons/TextInput.svelte'
  import InputSection from '$lib/commons/InputSection.svelte'
  import { extractValue } from '$lib/scripts/dataExtractor.ts'
  import categoryKeys from '$lib/inventory/types/categoryKeys.ts'

  export let data = undefined
  export let form

  let categoryId = extractValue(data, form, categoryKeys.id)
  let name = extractValue(data, form, categoryKeys.name)
  let buttonName = data === undefined ? 'Add' : 'Edit'
</script>

<form method="POST" class="mx-auto flex flex-col gap-3">
  <input name="categoryId" type="text" class="hidden" bind:value={categoryId} />
  <div class="mx-auto">
    <InputSection name="Operation" classes=" flex-row gap-2">
      <TextInput
        name="name"
        bind:value={name}
        placeholder="Category Name"
        classes=" bg-white text-black"
        error={!form ? undefined : form.name.message}
      />
    </InputSection>
  </div>
  <button class="btn btn-primary mx-auto">{buttonName}</button>
</form>
