<script>
  import { apiRequest } from '$lib/scripts/uiHttpRequests.ts'
  import { HttpMethods } from "$lib/types/httpMethods";

  export let domain;

  let dashboardName = ""

  const addDashboard = async () => {
    const ret = await apiRequest("/dashboards", HttpMethods.POST, {
      name: dashboardName,
      type: domain
    })
    location.reload()
  }
</script>
<div class="flex flex-col">
  <input type="text" placeholder="Dashboard Name" bind:value={dashboardName}
    class="input input-bordered w-full max-w-xs mx-auto mb-6 text-white" />
  <button class="btn btn-priamry" on:click={() => addDashboard()}>Add Dashboard</button>
</div>

