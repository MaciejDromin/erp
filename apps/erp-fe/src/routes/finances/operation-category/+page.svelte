<script lang="ts">
    import Pageable from "$lib/Pageable.svelte";
    import OperationCategoryTable from "$lib/finances/operation-category/OperationCategoryTable.svelte";
    import { MoneyOperationType } from "$lib/finances/types/financialTypes";

</script>

<div id="operation-category" class="flex flex-col gap-3 px-10 pt-10">
    <form method="POST" class="mx-auto flex flex-col gap-3 py-6">
        <div class="flex flex-row gap-3">
            <select name="operationType" class="select select-primary w-full max-w-xs">
                {#each Object.values(MoneyOperationType) as operationType}
                    <option value={operationType}>{operationType}</option>
                {/each}
            </select>
            <input
                name="operationName"
                type="text"
                placeholder="Operation Name"
                class="input input-bordered input-primary w-full max-w-xs"
            />
        </div>
        <button class="btn btn-primary">Add Row</button>
    </form>

    <Pageable endpoint="/finances/operation-category" component={OperationCategoryTable}/>
</div>
