<script>
  import Chart from '$lib/Chart.svelte'
  import Filter from '$lib/dashboard/Filter.svelte'
  import { onMount } from 'svelte'
  import { apiRequest } from './scripts/uiHttpRequests.ts'
  import { HttpMethods } from './types/httpMethods'
  import { FontAwesomeIcon } from '@fortawesome/svelte-fontawesome'
  import { faGear, faFilter } from '@fortawesome/free-solid-svg-icons'

  export let dashboardId
  export let widgetData
  let chartData = null

  let widgetDefinition

  console.log(widgetData)

  let widgetFilters = widgetData.filters

  const fetchData = async (filters) => {
    const ret = await apiRequest('/dashboards/widgets/data', HttpMethods.POST, {
      url: widgetData.datasource,
      filters: filters,
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

  const updateFilters = async (filters) => {
    await apiRequest(`/widgets/${widgetData.id}/filters`, HttpMethods.POST, filters)
  }

  const fetchWidgetDefinitions = async () => {
    if (widgetDefinition !== undefined) return
    const ret = await apiRequest(
      `/widgets/${widgetData.id}/definition`,
      HttpMethods.GET
    )
    widgetDefinition = await ret.json()
  }

  onMount(async () => {
    fetchData(widgetFilters)
  })

  $: widgetFilters, fetchData(widgetFilters)
  $: widgetFilters, updateFilters(widgetFilters)
</script>

<div class="flex flex-col">
  <div class="flex flex-row justify-between">
    <h3 class="text-xl">{widgetData.name}</h3>
    <div class="flex flex-row gap-3 items-center">
      <!--- TODO: Finish handling this placeholder icons --->
      <ul class="menu menu-horizontal p-0" on:click={fetchWidgetDefinitions}>
        <li>
          <details>
            <summary class="p-2"><FontAwesomeIcon icon={faFilter} /></summary>
            <ul
              class="text-black bg-gray-300"
              on:click|stopPropagation={() => {}}
            >
              {#if widgetDefinition !== undefined && widgetDefinition.availableFilters.length >= 0}
                {#each widgetDefinition.availableFilters as filter}
                  <li class="min-w-52"><Filter definition={filter} bind:filters={widgetFilters} /></li>
                {/each}
              {/if}
            </ul>
          </details>
        </li>
      </ul>
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
