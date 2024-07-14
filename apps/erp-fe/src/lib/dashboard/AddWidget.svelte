<script>
  import { apiRequest } from '$lib/scripts/uiHttpRequests.ts'
  import { HttpMethods } from "$lib/types/httpMethods";
  import { onMount } from "svelte";

  export let dashboardId;
  export let domain;

  let widgetOptions = []
  let widgetId = null
  let widgetDefinition = null

  const addWidget = async () => {
    console.log("test")
  }

  const fetchData = async () => {
    const ret = await apiRequest("/widgets/definitions/domain/" + domain, HttpMethods.GET)
    widgetOptions = await ret.json()
  }

  const getDefinition = async () => {
    const ret = await apiRequest("/widgets/definitions/" + widgetId, HttpMethods.GET)
    widgetDefinition = null
    widgetDefinition = await ret.json()
  }

  onMount(async () => {
    fetchData()
  })
</script>
<div class="flex flex-col">
  <!--- TODO: Finish adding widget --->
  <h2 class="text-xl mb-4">Choose Widget</h2>
  <select class="select w-full max-w-xs text-white mx-auto mb-4"
    bind:value={widgetId} on:change={() => getDefinition()}>
    {#each widgetOptions as option}
      <option value={option.id}>{option.name}</option>
    {/each}
  </select>
  {#if widgetDefinition !== null}
  {/if}
  <button class="btn btn-priamry" on:click={() => addWidget()}>Add Widget</button>
</div>

