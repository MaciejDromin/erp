<script lang="ts">
    import type { Breadcrumb, SidebarItem } from "./types/types";
    import Breadcrumbs from "./Breadcrumbs.svelte";
    export let breadcrumbs:Breadcrumb[]
    export let sidebar:SidebarItem[]

</script>

<div class="drawer drawer-open absolute top-0 bottom-0">
    <input id="my-drawer-2" type="checkbox" class="drawer-toggle" />
    <div class="drawer-content flex flex-col w-full h-full bg-white">
        <Breadcrumbs breadcrumbs={breadcrumbs} />
        <div class="p-3">
            <slot/>
        </div>
    </div>
    <div class="drawer-side h-full">
        <label for="my-drawer-2" class="drawer-overlay" />
        <ul class="hidden lg:block menu p-4 w-80 h-full bg-base-200 text-base-content">
            {#each sidebar as item}
                <li>
                    <a href={item.path}>{item.name}</a>
                    {#if item.subPaths.length > 0}
                        <ul>
                            {#each item.subPaths as subPath}
                                <li><a href={subPath.path}>{subPath.name}</a></li>
                            {/each}
                        </ul>
                    {/if}
                </li>
            {/each}
        </ul>
        <!-- TODO: here dynamically create list for small view -->
        <ul class="lg:hidden menu p-4 w-20 h-full bg-base-200 text-base-content">
            <!-- Sidebar content here -->
            <li><a>Small 1</a></li>
            <li><a>Small 2</a></li>
        </ul>
    </div>
</div>
