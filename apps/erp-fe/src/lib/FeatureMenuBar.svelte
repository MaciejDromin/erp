<script>
  import { goto } from '$app/navigation'
  import { apiRequest } from '$lib/scripts/uiHttpRequests.ts'
  import { HttpMethods } from '$lib/types/httpMethods.ts'
  import { genericStore } from '$lib/stores/genericStore.ts'

  export let config

  const gotoAdd = () => {
    goto(config.addButton.url)
  }

  const getIds = () => {
    let ret = $genericStore
    for (const key of config.delete.keys) {
      ret = ret[key]
    }
    if (ret.id === undefined) {
      return ret.map(({ uuid }) => uuid)
    }
    return ret.map(({ id }) => id)
  }

  const deleteFunc = async () => {
    const ids = getIds()
    const ret = await apiRequest('/commons', HttpMethods.DELETE, {
      endpoint: config.delete.endpoint,
      service: config.service,
      content: ids,
    })
    // TODO: Print ret if something didnt pass
    $genericStore.reload = true
  }

  const gotoEdit = () => {
    goto(config.editButton.url)
  }
</script>

<div class="navbar bg-neutral shadow-xl text-white h-8">
  <div class="flex-1">
    <a class="p-4 font-bold text-xl">{config.title}</a>
  </div>
  <div class="flex-none">
    <ul class="menu menu-horizontal px-1">
      <slot />
      <li>
        <button on:click={gotoAdd} class="btn btn-primary mr-3">Add</button>
      </li>
      <li>
        <button
          disabled={config.editButton.disabled}
          on:click={gotoEdit}
          class="btn btn-primary mr-3">Edit</button
        >
      </li>
      <li>
        <button
          disabled={config.deleteButton.disabled}
          class="btn btn-primary"
          on:click={() => deleteFunc()}>Delete</button
        >
      </li>
    </ul>
  </div>
</div>
