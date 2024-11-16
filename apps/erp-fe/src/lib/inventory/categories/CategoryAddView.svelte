<script>
  export let data = undefined

  let name = data === undefined ? '' : data.category.name
  let categoryId = data === undefined ? '' : data.category.id
  let buttonName = data === undefined ? 'Add' : 'Edit'
</script>

<form method="POST" class="mx-auto flex flex-row gap-3 py-6">
  <input name="categoryId" type="text" class="hidden" bind:value={categoryId} />
  <input
    name="name"
    type="text"
    placeholder="Type here"
    class="input input-bordered input-primary w-full max-w-xs"
    bind:value={name}
  />
  <button class="btn btn-primary">{buttonName} Row</button>
</form>
