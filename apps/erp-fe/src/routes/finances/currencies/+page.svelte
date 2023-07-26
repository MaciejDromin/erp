<script lang="ts">
    import Pageable from "$lib/Pageable.svelte";
    import CurrencyTable from "$lib/finances/currencies/CurrencyTable.svelte";

</script>

<div id="category" class="flex flex-col gap-3 px-10 pt-10">

    <Pageable endpoint="/finances/currencies" component={CurrencyTable}/>
</div>