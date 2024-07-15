<script>
  import Dashboard from './Dashboard.svelte'
  import Modal from '$lib/Modal.svelte'
  import AddDashboard from '$lib/dashboard/AddDashboard.svelte'
  import { apiRequest } from './scripts/uiHttpRequests.ts'
  import { HttpMethods } from './types/httpMethods'
  import { onMount } from 'svelte'
  import { FontAwesomeIcon } from '@fortawesome/svelte-fontawesome'
  import { faPlus } from '@fortawesome/free-solid-svg-icons'

  export let url
  export let domain

  let selectionOptions = []
  let dashboardData = null
  let selectedDashboard = null

  const fetchData = async () => {
    const ret = await apiRequest(url + '/selection', HttpMethods.GET)
    selectionOptions = await ret.json()
    const dashboardRet = await apiRequest(url, HttpMethods.GET)
    dashboardData = await dashboardRet.json()
    selectedDashboard = dashboardData.id
  }

  const changeDashboard = async () => {
    const dashboardRet = await apiRequest(
      '/dashboards/' + selectedDashboard,
      HttpMethods.GET
    )
    dashboardData = await dashboardRet.json()
  }

  onMount(async () => {
    fetchData()
  })
</script>

<div class="flex flex-col w-full h-full">
  <div class="flex flex-row w-full h-full justify-between py-8">
    <select
      class="select w-full max-w-xs text-white"
      bind:value={selectedDashboard}
      on:change={() => changeDashboard()}
    >
      {#each selectionOptions as opt}
        <option id={opt.id} selected={opt.defaultForType} value={opt.id}
          >{opt.name}</option
        >
      {/each}
    </select>
    <div id="options">
      <Modal modalId="add_dashboard_modal" buttonName="Add Dashboard">
        <AddDashboard {domain} />
      </Modal>
      <!---<button class="btn btn-primary"><FontAwesomeIcon icon={faPlus} /> Add</button>--->
    </div>
  </div>
  {#if dashboardData !== null}
    <Dashboard data={dashboardData} items={dashboardData.widgets} {domain} />
  {/if}
</div>
