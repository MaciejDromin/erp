<script>
  import Chart from '$lib/Chart.svelte'
  import { onMount } from 'svelte'
  import { apiRequest } from './scripts/uiHttpRequests.ts'
  import { HttpMethods } from './types/httpMethods'
  import { FontAwesomeIcon } from '@fortawesome/svelte-fontawesome'
  import { faGear, faFilter } from '@fortawesome/free-solid-svg-icons'

  export let widgetData
  let chartData = null

  const fetchData = async () => {
    const ret = await apiRequest('/dashboards/widgets/data', HttpMethods.POST, {
      url: widgetData.datasource,
      filters: widgetData.filters,
    })
    chartData = await ret.json()
  }

  onMount(async () => {
    fetchData()
  })
</script>

<div class="flex flex-col">
  <div class="flex flex-row justify-between">
    <h3 class="text-xl">{widgetData.name}</h3>
    <div class="flex flex-row gap-3">
      <!--- TODO: Finish handling this placeholder icons --->
      <FontAwesomeIcon icon={faFilter} />
      <FontAwesomeIcon icon={faGear} />
      <ul class="menu lg:menu-horizontal bg-base-200 rounded-box lg:mb-64">
        <li>
          <details open>
            <summary>Parent item</summary>
            <ul>
              <li><a>Submenu 1</a></li>
              <li><a>Submenu 2</a></li>
              <li>
                <details open>
                  <summary>Parent</summary>
                  <ul>
                    <li><a>item 1</a></li>
                    <li><a>item 2</a></li>
                  </ul>
                </details>
              </li>
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
