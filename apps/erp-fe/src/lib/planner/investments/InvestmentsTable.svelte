<script lang="ts">
    export let data: any = undefined;
</script>

<div class="overflow-x-auto text-primary-content mx-auto">
    <!-- <table class="table table-zebra"> -->
    <table class="table">
        <!-- head -->
        <thead class="text-primary-content">
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Total Cost</th>
                <th>Currency</th>
                <th>Total Estimated Time</th>
            </tr>
        </thead>
        <tbody>
            {#if data !== undefined}
                {#each data.content as investment}
                    <tr>
                        <td>{investment.id}</td>
                        <td>{investment.name}</td>
                        <td>{investment.totalEstimatedTime}</td>
                        <td>{investment.totalCost.value}</td>
                        <td>{investment.totalCost.currencyCode}</td>
                    </tr>
                {/each}
            {/if}
        </tbody>
    </table>
</div>