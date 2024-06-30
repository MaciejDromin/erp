<script lang="ts">
    import type { LayoutData } from "./$types";
    import "../app.css";
    import MainContent from "../lib/MainContent.svelte";
    import TopNav from "../lib/TopNav.svelte";
    import calculateBreadcrumbs from "../lib/scripts/calculateBreadcrumbs";
    import { page } from "$app/stores"
    import '@fortawesome/fontawesome-svg-core/styles.css'
    import { config } from '@fortawesome/fontawesome-svg-core'

    config.autoAddCss = false

    export let data: LayoutData
</script>
<main class="flex flex-col min-h-screen justify-between">
    <TopNav/>
    <div class="relative grow">
        <MainContent breadcrumbs={calculateBreadcrumbs($page.url.pathname)} sidebar={data.paths}>
            <slot />
        </MainContent>
    </div>
</main>
