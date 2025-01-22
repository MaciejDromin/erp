<script lang="ts">
  import Pageable from '$lib/Pageable.svelte'
  import InvestmentsTable from '$lib/planner/investments/InvestmentsTable.svelte'
</script>

<div id="investment" class="flex flex-col gap-3 px-10 pt-10">
  <div class="mx-auto flex flex-col gap-3 py-6">
    <a href="/planner/investments/add">
      <button class="btn btn-primary">Add new Investment</button>
    </a>
  </div>

  <Pageable endpoint="/planner/investments" component={InvestmentsTable} />
</div>
