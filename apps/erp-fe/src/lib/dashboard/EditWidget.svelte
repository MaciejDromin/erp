<script lang="ts">
  import { apiRequest } from '$lib/scripts/uiHttpRequests.ts'
  import { HttpMethods } from '$lib/types/httpMethods'
  import { WidgetTypes } from '$lib/dashboard/WidgetTypes.ts'

  export let data

  let name = data.name
  let type = data.type

  const editWidget = async () => {
    const ret = await apiRequest('/widgets/' + data.id, HttpMethods.POST, {
      name: name,
      type: type,
    })
    location.reload()
  }
</script>

<div class="flex flex-col">
  <h2 class="mb-4 text-2xl text-black">Edit Widget</h2>
  <label class="input input-bordered flex items-center gap-2 text-white mb-4">
    Name
    <input
      type="text"
      class="grow"
      placeholder="Awesome widget"
      bind:value={name}
    />
  </label>
  <select
    class="select select-info w-full max-w-xs text-white mb-4"
    bind:value={type}
  >
    <option disabled selected>Select type</option>
    {#each Object.values(WidgetTypes) as widgetType}
      <option value={widgetType}>{widgetType}</option>
    {/each}
  </select>
  <button class="btn btn-primary mx-auto" on:click={() => editWidget()}
    >Edit Widget</button
  >
</div>
