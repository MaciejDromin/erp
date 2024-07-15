<script lang="ts">
  export let data: any = undefined
</script>

<div class="overflow-x-auto text-primary-content mx-auto">
  <!-- <table class="table table-zebra">  -->
  <table class="table">
    <!-- head -->
    <thead class="text-primary-content">
      <tr>
        <th>Code</th>
        <th>Currency</th>
        <th>Rate</th>
        <th>Effective Date</th>
      </tr>
    </thead>
    <tbody>
      {#if data !== undefined}
        {#each data.content as currency}
          <tr>
            <td>{currency.code}</td>
            <td>{currency.currency}</td>
            <td>{currency.mid}</td>
            <td>{currency.effectiveDate}</td>
          </tr>
        {/each}
      {/if}
    </tbody>
  </table>
</div>
