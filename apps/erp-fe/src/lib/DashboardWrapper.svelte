<script>
  import Dashboard from './Dashboard.svelte'
  import { apiRequest } from './scripts/uiHttpRequests.ts'
  import { HttpMethods } from "./types/httpMethods";
  import { onMount } from "svelte";
  
  export let url;
  export let domain;

  let selectionOptions = []
  let dashboardData = null

  const fetchData = async () => {
    const ret = await apiRequest(url + "/selection", HttpMethods.GET)
    selectionOptions = await ret.json()
    const dashboardRet = await apiRequest(url, HttpMethods.GET)
    dashboardData = await dashboardRet.json()
  }

  onMount(async () => {
    fetchData()
  })
</script>

<div class="flex flex-col w-full h-full">
  <div class="flex flex-row w-full h-full justify-between py-8">
    <select class="select w-full max-w-xs text-white">
      {#each selectionOptions as opt}
        <option id={opt.id} selected={opt.defaultForType}>{opt.name}</option> 
      {/each}
    </select>
    <div id="options">
    </div>
  </div>
  {#if dashboardData !== null}
    <Dashboard data={dashboardData}/>
  {/if}
</div>
