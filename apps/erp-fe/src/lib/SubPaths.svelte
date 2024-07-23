<script lang="ts">
  import type { SidebarItem } from './types/types'
  import SubPaths from './SubPaths.svelte'
  export let subPaths: SidebarItem[]
</script>

{#if subPaths.length > 0}
  <ul>
    {#each subPaths as subPath}
      <li>
        <a href={subPath.path}>{subPath.name}</a>
        <SubPaths subPaths={subPath.subPaths} />
      </li>
    {/each}
  </ul>
{/if}
