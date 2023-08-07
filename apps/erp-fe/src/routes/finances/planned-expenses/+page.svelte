<script lang="ts">
    import Modal from "$lib/Modal.svelte";
    import Pageable from "$lib/Pageable.svelte";
    import { operationCategoriesStore } from "$lib/finances/stores/selectedOperationCategory";
    import OperationCategoryTable from "$lib/finances/operation-category/OperationCategoryTable.svelte";
    import { onMount } from "svelte";
    import { MoneyOperationType, Month } from "$lib/finances/types/financialTypes";
    import PlannedExpensesTable from "$lib/finances/planned-expenses/PlannedExpensesTable.svelte";

    let categories:any[] = []

    onMount(() => {
        $operationCategoriesStore = []
    })

    operationCategoriesStore.subscribe((cat) => {
        categories = [...cat]
        categories = categories
    })

    const determineButtonName = (arr:any[]):string => {
        if (arr.length === 0) return "select category"
        return `${arr.length} category selected`
    }

</script>

<div id="planned-expenses" class="flex flex-col gap-3 px-10 pt-10">
    <form method="POST" class="mx-auto flex flex-col gap-3 py-6" action="?/create">
        <div class="flex flex-row gap-3">
            <input
            name="plannedAmount"
            type="text"
            placeholder="Planned Amount"
            class="input input-bordered input-primary w-full max-w-xs"
            />
            <input
                name="currencyCode"
                type="text"
                placeholder="Currency Code"
                class="input input-bordered input-primary w-full max-w-xs"
            />
        </div>
        <div class="flex flex-row gap-3">
            <input
                name="operationDescription"
                type="text"
                placeholder="Operation Description"
                class="input input-bordered input-primary w-full"
            />
        </div>
        <div class="flex flex-row gap-3">
            <input
            name="plannedYear"
            type="text"
            placeholder="Planned Year"
            class="input input-bordered input-primary w-full max-w-xs"
            />
            <select name="plannedMonth" class="select select-primary w-full max-w-xs">
                {#each Object.values(Month) as month}
                    <option value={month}>{month}</option>
                {/each}
            </select>
        </div>
        
        <div class="flex flex-row gap-3">
            <select multiple name="categoryId" class="p-4 mr-auto hidden" bind:value={categories}>
                {#each categories as category}
                    <option value={category}></option>
                {/each}
            </select>
            <div class="mx-auto">
                <Modal modalId="category_modal" buttonName={determineButtonName(categories)}>
                    <Pageable endpoint="/finances/operation-category" component={OperationCategoryTable} additionalSearch={`&operationType=${MoneyOperationType.EXPENSES}`}/>
                </Modal>
            </div>
        </div>
        <button class="btn btn-primary mx-auto">Add Row</button>
    </form>

    <Pageable endpoint="/finances/planned-expenses" component={PlannedExpensesTable}/>
</div>
