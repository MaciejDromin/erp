<script>
  import { WidgetTypes } from "$lib/dashboard/WidgetTypes.ts"
  export let definition
  export let creationData
  
  let name
  let type
  let filters = {}

  const updateFields = () => {
    creationData.name = name
    creationData.widgetType = type
    creationData.filters = filters
  }

</script>
<div>
  <label class="input input-bordered flex items-center gap-2 text-white mb-4">
    Name
    <input type="text" class="grow" placeholder="Awesome widget"
      bind:value={name} on:change={updateFields} />
  </label>
  <select class="select select-info w-full max-w-xs text-white mb-4"
    bind:value={type} on:change={updateFields}>
    <option disabled selected>Select type</option>
    {#each Object.values(WidgetTypes) as widgetType}
      <option value={widgetType}>{widgetType}</option>
    {/each}
    <!--- TODO: Later handle adding filters --->
  </select>
</div>
