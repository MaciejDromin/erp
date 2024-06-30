<script>
  import { apiRequest } from '$lib/scripts/uiHttpRequests.ts'
  import { HttpMethods } from "$lib/types/httpMethods";

  export let dashboardId;

  const addWidget = async () => {
    console.log("test")
  }
</script>
<div class="flex flex-col">
  <!--- TODO: Finish adding widget --->
  <button class="btn btn-priamry" on:click={() => addWidget()}>Add Widget</button>
</div>

