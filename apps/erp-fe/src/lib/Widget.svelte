<script>
  import Chart from "$lib/Chart.svelte"
  import { onMount } from "svelte";
  import { apiRequest } from './scripts/uiHttpRequests.ts'
  import { HttpMethods } from "./types/httpMethods";

  export let widgetData;
  let chartData = null
  console.log(widgetData)
  // pass data to chart comp

  const fetchData = async () => {
    const ret = await apiRequest("/dashboards/widgets/data", HttpMethods.POST, {
      url: widgetData.datasource,
      filters: widgetData.filters
    })
    chartData = await ret.json()
  }

  onMount(async () => {
    fetchData()
  })
</script>
<div class="flex flex-col">
  <div class="flex flex-row">
    <h3 class="text-xl">{widgetData.name}</h3>
    <!--- TODO: Add here options --->
  </div>
  {#if chartData !== null}
    <Chart chartData={chartData} chartType={widgetData.widgetType} />
  {/if}
</div>
