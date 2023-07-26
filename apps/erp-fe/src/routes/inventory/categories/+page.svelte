<script lang="ts">
    import Pageable from "$lib/Pageable.svelte";
    import CategoryTable from "$lib/inventory/categories/CategoryTable.svelte";
    import { categoriesStore } from "$lib/inventory/stores/selectedCategories";
    import { onMount } from "svelte";

    onMount(() => {
        $categoriesStore = []
    })

</script>

<div id="category" class="flex flex-col gap-3 px-10 pt-10">
    <form method="POST" class="mx-auto flex flex-row gap-3 py-6">
        <input
            name="name"
            type="text"
            placeholder="Type here"
            class="input input-bordered input-primary w-full max-w-xs"
        />
        <button class="btn btn-primary">Add Row</button>
    </form>

    <Pageable endpoint="/inventory/categories" component={CategoryTable}/>
</div>