<script lang="ts">
  import { apiRequest } from './scripts/uiHttpRequests'
  import { HttpMethods } from './types/httpMethods'
  import { onMount } from 'svelte'
  import { genericStore } from '$lib/stores/genericStore.ts'

  export let endpoint: string
  export let component: any
  export let additionalSearch: any = undefined
  export let additionalParams: any = {}

  let currentPage: number = 0
  let maxPage: number = 0
  let data: any

  const fetchData = async (requestedPage: number, search: any) => {
    let additionalQuery = search === undefined ? '' : search
    const ret = await apiRequest(
      endpoint + '?page=' + requestedPage + additionalQuery,
      HttpMethods.GET
    )
    data = await ret.json()
    maxPage = data.totalPages
  }

  onMount(async () => {
    fetchData(currentPage, additionalSearch)
  })

  const doesLowerPageExists = (page: number): boolean => {
    if (page == 0) return false
    return true
  }

  const doesHigherPageExists = (page: number, mPage: number): boolean => {
    if (page >= mPage - 1) return false
    return true
  }

  const shouldReRender = () => {
    if ($genericStore.reload) {
      fetchData(currentPage, additionalSearch)
      $genericStore.reload = false
    }
  }

  $: $genericStore, shouldReRender()
</script>

<div class="flex flex-col">
  <div>
    <svelte:component
      this={component}
      {...{ data: data }}
      {...additionalParams}
    />
  </div>

  <div class="join flex mx-auto mt-3">
    <button
      class={`join-item btn disabled:opacity-75 ${!doesLowerPageExists(currentPage) ? 'btn-disable opacity-75' : ''}`}
      on:click|preventDefault={() => fetchData(--currentPage, additionalSearch)}
      >«</button
    >
    <button class="join-item btn btn-disable">Page {currentPage + 1}</button>
    <button
      class={`join-item btn disabled:opacity-75 ${!doesHigherPageExists(currentPage, maxPage) ? 'btn-disable opacity-75' : ''}`}
      on:click|preventDefault={() => fetchData(++currentPage, additionalSearch)}
      >»</button
    >
  </div>
</div>

<style>
  .btn-disable {
    pointer-events: none;
  }
</style>
