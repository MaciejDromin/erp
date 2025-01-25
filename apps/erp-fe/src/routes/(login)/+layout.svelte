<script lang="ts">
  import '../../app.css'
  import TopNav from '$lib/TopNav.svelte'
  import '@fortawesome/fontawesome-svg-core/styles.css'
  import { config } from '@fortawesome/fontawesome-svg-core'
</script>

<main class="flex flex-col min-h-screen bg-white">
  <TopNav />
  <slot />
</main>
