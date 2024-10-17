<script lang="ts">
  import type { SidebarItem } from './types/types'
  import SubPaths from './SubPaths.svelte'
  export let subPaths: SidebarItem[]
</script>

{#if subPaths.length > 0}
  <ul>
    {#each subPaths as subPath}
      <li class="flex-nowrap">
        <a
          href={subPath.path}
          class={subPath.subPaths.length <= 0 ? 'truncate inline-block' : 'text-slate-100 truncate inline-block'}
          >{subPath.name}</a
        >
        <SubPaths subPaths={subPath.subPaths} />
      </li>
    {/each}
  </ul>
{/if}
