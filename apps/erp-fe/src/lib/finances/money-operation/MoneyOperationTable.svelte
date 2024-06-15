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
                <th>Effective Date</th>
            </tr>
        </thead>
        <tbody>
            {#if data !== undefined}
                {#each data.content as moneyOperation}
                    <tr>
                        <td>{moneyOperation.uuid}</td>
                        <td>{moneyOperation.operationType}</td>
                        <td>{moneyOperation.operationCategory === null ? "EMPTY" : moneyOperation.operationCategory.operationName}</td>
                        <td>{moneyOperation.operationDescription}</td>
                        <td>{moneyOperation.amount.value}</td>
                        <td>{moneyOperation.amount.currencyCode}</td>
                        <td>{moneyOperation.effectiveDate}</td>
                    </tr>
                {/each}
            {/if}
        </tbody>
    </table>
</div>
