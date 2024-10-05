<script lang="ts">
  import type { Breadcrumb, SidebarItem } from './types/types'
  import Breadcrumbs from './Breadcrumbs.svelte'
  import SubPaths from './SubPaths.svelte'
  export let breadcrumbs: Breadcrumb[]
  export let sidebar: SidebarItem[]
</script>

<div class="drawer drawer-open absolute top-0 bottom-0">
  <input id="my-drawer-2" type="checkbox" class="drawer-toggle" />
  <div class="drawer-content flex flex-col w-full h-full bg-white">
    <Breadcrumbs {breadcrumbs} />
    <div>
      <slot />
    </div>
  </div>
  <div class="drawer-side h-full">
    <label for="my-drawer-2" class="drawer-overlay" />
    <ul
      class="hidden lg:block menu p-4 w-80 min-h-full bg-base-200 text-base-content"
    >
      {#each sidebar as item}
        <li>
          <a href={item.path}>{item.name}</a>
          <SubPaths subPaths={item.subPaths} />
        </li>
      {/each}
    </ul>
    <!-- TODO: here dynamically create list for small view -->
    <ul class="lg:hidden menu p-4 w-20 min-h-full bg-base-200 text-base-content">
      <!-- Sidebar content here -->
      <li><a>Small 1</a></li>
      <li><a>Small 2</a></li>
    </ul>
  </div>
</div>
