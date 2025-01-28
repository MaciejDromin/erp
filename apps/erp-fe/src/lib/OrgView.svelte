<script lang="ts">
  import { onMount } from 'svelte'
  import { apiRequest } from '$lib/scripts/uiHttpRequests.ts'
  import { HttpMethods } from '$lib/types/httpMethods'

  let orgs = []
  let currentOrg = ''

  onMount(async () => {
    const ret = await apiRequest('/orgs', HttpMethods.GET)
    const data = await ret.json()
    orgs = data.orgs
    currentOrg = data.current
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
        <a
          class={currentOrg === org.orgId ? 'text-primary' : ''}
          on:click={() => updateOrg(org.orgId)}>{org.name}</a
        >
      </li>
    {/each}
  </ul>
</details>
