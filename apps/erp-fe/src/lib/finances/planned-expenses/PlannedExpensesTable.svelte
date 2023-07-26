<script lang="ts">
    export let data: any = undefined;

    let actualAmountByIdMap: { [key:string]:string } = {}

</script>

<div class="overflow-x-auto text-primary-content mx-auto">
    <table class="table">
        <thead class="text-primary-content">
            <tr>
                <th>ID</th>
                <th>Operation Category</th>
                <th>Operation Description</th>
                <th>Planned Amount</th>
                <th>Actual Amount</th>
                <th>Currency</th>
                <th>Planned Year</th>
                <th>Planned Month</th>
                <th>Status</th>
                <th>Finalized Date</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            {#if data !== undefined}
                {#each data.content as plannedExpenses}
                    <tr>
                        <td>{plannedExpenses.uuid}</td>
                        <td
                            >{plannedExpenses.operationCategory
                                .operationName}</td
                        >
                        <td>{plannedExpenses.operationDescription}</td>
                        <td>{plannedExpenses.plannedAmount.value}</td>
                        <td>
                            {#if plannedExpenses.plannedExpensesStatus === "PLANNED"}
                                <input
                                    name="amountHolder"
                                    class="input input-bordered input-primary w-full max-w-xs text-white"
                                    bind:value={actualAmountByIdMap[plannedExpenses.uuid]}
                                    type="text"
                                />
                            {:else}
                                {plannedExpenses.actualAmount.value}
                            {/if}
                        </td>
                        <td>{plannedExpenses.plannedAmount.currencyCode}</td>
                        <td>{plannedExpenses.plannedYear}</td>
                        <td>{plannedExpenses.plannedMonth}</td>
                        <td>{plannedExpenses.plannedExpensesStatus}</td>
                        <td
                            >{plannedExpenses.finalizedDate === undefined
                                ? "No Value"
                                : plannedExpenses.finalizedDate}</td
                        >
                        <td>
                            {#if plannedExpenses.plannedExpensesStatus !== "PLANNED"}
                                No Actions
                            {:else}
                                <div class="flex flex-row gap-3">
                                    <form method="POST" action="?/complete">
                                        <input
                                            name="plannedExpensesId"
                                            class="hidden"
                                            value={plannedExpenses.uuid}
                                            type="text"
                                        />
                                        <input
                                            name="actualAmount"
                                            class="hidden"
                                            bind:value={actualAmountByIdMap[plannedExpenses.uuid]}
                                            type="text"
                                        />
                                        <input
                                            name="currency"
                                            class="hidden"
                                            value={plannedExpenses.plannedAmount.currencyCode}
                                            type="text"
                                        />
                                        <button class="btn btn-primary mx-auto"
                                            >Complete</button
                                        >
                                    </form>
                                    <form method="POST" action="?/abandon">
                                        <input
                                            name="plannedExpensesId"
                                            class="hidden"
                                            value={plannedExpenses.uuid}
                                            type="text"
                                        />
                                        <button class="btn btn-primary mx-auto"
                                            >Abandon</button
                                        >
                                    </form>
                                </div>
                            {/if}
                        </td>
                    </tr>
                {/each}
            {/if}
        </tbody>
    </table>
</div>