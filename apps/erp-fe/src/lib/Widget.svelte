<script>
  import Chart from '$lib/Chart.svelte'
  import { onMount } from 'svelte'
  import { apiRequest } from './scripts/uiHttpRequests.ts'
  import { HttpMethods } from './types/httpMethods'
  import { FontAwesomeIcon } from '@fortawesome/svelte-fontawesome'
  import { faGear, faFilter } from '@fortawesome/free-solid-svg-icons'

  export let dashboardId
  export let widgetData
  let chartData = null

  const fetchData = async () => {
    const ret = await apiRequest('/dashboards/widgets/data', HttpMethods.POST, {
      url: widgetData.datasource,
      filters: widgetData.filters,
    })
    chartData = await ret.json()
  }

  const deleteWidget = async () => {
    const ret = await apiRequest(
      `/dashboards/${dashboardId}/widgets/${widgetData.id}`,
      HttpMethods.DELETE
    )
    location.reload()
  }

  onMount(async () => {
    fetchData()
  })
</script>

<div class="flex flex-col">
  <div class="flex flex-row justify-between">
    <h3 class="text-xl">{widgetData.name}</h3>
    <div class="flex flex-row gap-3 items-center">
      <!--- TODO: Finish handling this placeholder icons --->
      <button><FontAwesomeIcon icon={faFilter} /></button>
      <ul class="menu menu-horizontal p-0">
        <li>
          <details>
            <summary class="p-2"><FontAwesomeIcon icon={faGear} /></summary>
            <ul class="text-white">
              <li><button>Edit</button></li>
              <li><button on:click={deleteWidget}>Delete</button></li>
            </ul>
          </details>
        </li>
      </ul>
    </div>
  </div>
  {#if chartData !== null}
    <Chart {chartData} chartType={widgetData.widgetType} />
  {/if}
</div>
