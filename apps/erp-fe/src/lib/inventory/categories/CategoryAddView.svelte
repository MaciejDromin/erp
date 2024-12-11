<script>
  import TextInput from '$lib/commons/TextInput.svelte'
  import InputSection from '$lib/commons/InputSection.svelte'

  export let data = undefined

  let name = data === undefined ? '' : data.category.name
  let categoryId = data === undefined ? '' : data.category.id
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
      />
    </InputSection>
  </div>
  <button class="btn btn-primary mx-auto">{buttonName}</button>
</form>
