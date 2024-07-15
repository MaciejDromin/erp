<script lang="ts">
  export let data: any = undefined
</script>

<div class="overflow-x-auto text-primary-content mx-auto">
  <!-- <table class="table table-zebra">  -->
  <table class="table">
    <!-- head -->
    <thead class="text-primary-content">
      <tr>
        <th>ID</th>
        <th>Operation Type</th>
        <th>Operation Category</th>
        <th>Operation Description</th>
        <th>Amount</th>
        <th>Currency</th>
        <th>Next Applicable Month</th>
      </tr>
    </thead>
    <tbody>
      {#if data !== undefined}
        {#each data.content as periodical}
          <tr>
            <td>{periodical.uuid}</td>
            <td>{periodical.operationCategory.operationType}</td>
            <td>{periodical.operationCategory.operationName}</td>
            <td>{periodical.operationDescription}</td>
            <td>{periodical.amount.value}</td>
            <td>{periodical.amount.currencyCode}</td>
            <td>{periodical.nextApplicableMonth}</td>
          </tr>
        {/each}
      {/if}
    </tbody>
  </table>
</div>
