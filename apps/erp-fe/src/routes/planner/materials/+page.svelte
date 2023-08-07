<script lang="ts">
    import Pageable from "$lib/Pageable.svelte";
    import MaterialsTable from "$lib/planner/materials/MaterialsTable.svelte";
    import { ItemUnit } from "$lib/inventory/types/inventoryTypes";

</script>

<div id="material" class="flex flex-col gap-3 px-10 pt-10">
    <form method="POST" class="mx-auto flex flex-col gap-3 py-6">
        <div class="flex flex-row gap-3">
            <input
                name="name"
                type="text"
                placeholder="Name"
                class="input input-bordered input-primary w-full max-w-xs"
            />
            <select name="unit" class="select select-primary w-full max-w-xs">
                {#each Object.values(ItemUnit) as unit}
                    <option value={unit}>{unit}</option>
                {/each}
            </select>
        </div>
        <div class="flex flex-row gap-3">
            <input
                name="value"
                type="text"
                placeholder="Value"
                class="input input-bordered input-primary w-full max-w-xs"
            />
            <input
                name="currency"
                type="text"
                placeholder="Currency"
                class="input input-bordered input-primary w-full max-w-xs"
            />
        </div>
        <div class="flex flex-row gap-3">
            <input
                name="source"
                type="text"
                placeholder="Source"
                class="input input-bordered input-primary w-full max-w-xs"
            />
            <button class="btn btn-primary">Add Row</button>
        </div>
        
    </form>

    <Pageable endpoint="/planner/materials" component={MaterialsTable}/>
</div>