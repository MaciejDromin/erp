<script>
  import { apiRequest } from '$lib/scripts/uiHttpRequests.ts'
  import { HttpMethods } from '$lib/types/httpMethods'

  export let definition
  export let filters

  let val
  let fieldDisabled = false
  let dependsOn = definition.dependsOn === null ? false : true
  let selectData

  const updateFilters = () => {
    filters[definition.name.toLowerCase()] = val
    filters = filters
  }

  const determineDisabled = () => {
    if (!dependsOn) {
      return false
    }
    for (let i = 0; i < definition.dependsOn.length; i++) {
      let filterVal = filters[definition.dependsOn[i].toLowerCase()]
      if (filterVal === undefined || filterVal === '') return true
    }
    return false
  }

  const getData = async () => {
    let filtersToSend = undefined
    if (dependsOn) {
      filtersToSend = {}
      for (let i = 0; i < definition.dependsOn.length; i++) {
        let fieldName = definition.dependsOn[i].toLowerCase()
        filtersToSend[fieldName] = filters[fieldName]
      }
    }
    const data = {
      datasource: definition.datasource,
      filters: filtersToSend,
    }
    const ret = await apiRequest(
      '/widgets/filters/datasource',
      HttpMethods.POST,
      data
    )
    let retData = await ret.json()
    selectData = retData
  }

  $: filters, (fieldDisabled = determineDisabled())
  $: filters, getData()
</script>

{#if definition.filterType === 'SELECT'}
  {#if definition.datasource !== null}
    <select
      class="select select-info w-full max-w-xs text-white mb-4"
      bind:value={val}
      on:change={updateFilters}
      disabled={fieldDisabled}
    >
      <option disabled selected>Select {definition.name}</option>
      {#if selectData && selectData.length >= 0}
        {#each selectData as fd}
          <option value={fd}>{fd}</option>
        {/each}
      {/if}
    </select>
  {:else}{/if}
{:else if definition.filterType === 'INTEGER'}
  <input
    name={definition.name}
    bind:value={val}
    on:change={updateFilters}
    disabled={fieldDisabled}
  />
{:else if definition.filterType === 'DOUBLE'}
  <input
    name={definition.name}
    bind:value={val}
    on:change={updateFilters}
    disabled={fieldDisabled}
  />
{:else if definition.filterType === 'BOOLEAN'}
  <input
    name={definition.name}
    bind:value={val}
    on:change={updateFilters}
    disabled={fieldDisabled}
  />
{:else if definition.filterType === 'STRING'}
  <input
    name={definition.name}
    bind:value={val}
    on:change={updateFilters}
    disabled={fieldDisabled}
  />
{/if}
