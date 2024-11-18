<!-- TODO: create content -->
<script lang="ts">
  import { onMount } from 'svelte';
  import { REPORTS_URL } from '$lib/scripts/urls.ts'

  onMount(() => {
    const wsUrl = "ws://" + REPORTS_URL.slice(REPORTS_URL.lastIndexOf("/") + 1) + "/test"
    const ws = new WebSocket(wsUrl)

    ws.addEventListener('open', (e) => {
      console.log("Connected!")
    })

    ws.addEventListener('message', (m) => {
      console.log(m.data)
    })

  }) 

</script>
