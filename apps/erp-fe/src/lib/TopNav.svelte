<script lang="ts">
  import { apiRequest } from '$lib/scripts/uiHttpRequests.ts'
  import { HttpMethods } from '$lib/types/httpMethods'
  import OrgView from '$lib/OrgView.svelte'
  const logout = async () => {
    const ret = await apiRequest('/logout', HttpMethods.GET)
  }
</script>

<div class="navbar bg-base-300 h-8">
  <div class="flex-1">
    <a href="/" class="btn btn-ghost normal-case text-xl">Dromin ERP</a>
  </div>
  <div class="flex-none">
    <ul class="menu menu-horizontal px-1 mr-16">
      <li>
        <details>
          <summary class="ml-16">Menu</summary>
          <ul class="bg-base-100 rounded-t-none p-2 z-[999] min-w-[182px]">
            <li><OrgView /></li>
            <li><a on:click={logout}>Logout</a></li>
          </ul>
        </details>
      </li>
    </ul>
  </div>
</div>
