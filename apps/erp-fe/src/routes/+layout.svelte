<script lang="ts">
  import type { LayoutData } from './$types'
  import '../app.css'
  import MainContent from '../lib/MainContent.svelte'
  import TopNav from '../lib/TopNav.svelte'
  import calculateBreadcrumbs from '../lib/scripts/calculateBreadcrumbs'
  import { page } from '$app/stores'
  import '@fortawesome/fontawesome-svg-core/styles.css'
  import { config } from '@fortawesome/fontawesome-svg-core'
  import EventOverlay from '$lib/events/EventOverlay.svelte'
  import { onMount } from 'svelte'
  import { REPORTS_URL } from '$lib/scripts/urls.ts'

  config.autoAddCss = false

  export let data: LayoutData

  let eventQueue = []
  let triggerClose

  onMount(async () => {
    configureWs()
  })

  const configureWs = async () => {
    const wsUrl =
      'ws://' + REPORTS_URL.slice(REPORTS_URL.lastIndexOf('/') + 1) + '/reports'
    const ws = new WebSocket(wsUrl)

    ws.addEventListener('open', (e) => {
      // console.log("Connected!")
    })

    ws.addEventListener('message', (m) => {
      if (m.data !== 'Connected') {
        eventQueue.push(JSON.parse(m.data))
        eventQueue = eventQueue
      }
    })
  }

  const buildEventData = (event) => {
    let fileLocation = event.content.fileLocation
    let eventData = {
      url: fileLocation, // TODO: Most likely need to adjust url
      name: fileLocation.slice(fileLocation.indexOf('/') + 1),
    }

    return eventData
  }

  const shouldRender = (event) => {
    if (event.content.status !== 'COMPLETED') {
      eventQueue.shift()
      return false
    }
    return true
  }

  const removeAndCloseEvent = () => {
    if (triggerClose) {
      eventQueue.shift()
      eventQueue = eventQueue
      triggerClose = false
    }
  }

  $: eventQueue
  $: triggerClose, removeAndCloseEvent()
</script>

<main class="flex flex-col min-h-screen justify-between">
  {#if eventQueue.length > 0 && shouldRender(eventQueue[0])}
    <EventOverlay eventData={buildEventData(eventQueue[0])} bind:triggerClose />
  {/if}
  <TopNav />
  <div class="relative grow">
    <MainContent
      breadcrumbs={calculateBreadcrumbs($page.url.pathname)}
      sidebar={data.paths}
    >
      <slot />
    </MainContent>
  </div>
</main>
