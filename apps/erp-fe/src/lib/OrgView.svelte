<script lang="ts">
  import { onMount } from 'svelte'
  import { apiRequest } from '$lib/scripts/uiHttpRequests.ts'
  import { HttpMethods } from '$lib/types/httpMethods'

  let orgs = []

  onMount(async () => {
    const ret = await apiRequest('/orgs', HttpMethods.GET)
    orgs = (await ret.json()).orgs
  })

  const updateOrg = async (orgId) => {
    const ret = await apiRequest(
      `/orgs/update?newOrgId=${orgId}`,
      HttpMethods.POST
    )
  }
</script>

<details>
  <summary>Organizations</summary>
  <ul>
    {#each orgs as org}
      <li>
        <a on:click={() => updateOrg(org.orgId)}>{org.name}</a>
      </li>
    {/each}
  </ul>
</details>
