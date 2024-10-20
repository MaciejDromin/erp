<script lang="ts">
  import type { Breadcrumb } from './types/types'
  export let breadcrumbs: Breadcrumb[]

  const capitalize = (str) => {
    if (str.indexOf('-') >= 0) {
      let arr = str.split('-')
      return arr.map((s) => s.charAt(0).toUpperCase() + s.slice(1)).join(' ')
    }
    return str.charAt(0).toUpperCase() + str.slice(1)
  }
</script>

<div
  class="text-sm breadcrumbs text-primary-content w-full pt-3 pl-3 bg-base-100 text-white"
>
  <ul>
    {#each breadcrumbs as breadcrumb}
      <li><a href={breadcrumb.path}>{capitalize(breadcrumb.name)}</a></li>
    {/each}
  </ul>
</div>
