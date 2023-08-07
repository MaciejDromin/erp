<script lang="ts">
    import { onDestroy } from "svelte";
    import { selectedLabours } from "../stores/selectedLabours";

    export let data: any = undefined;
    export let labourPos:any = undefined;
    export let phasePos:any = undefined;
    let selectedLabourId:string = ''

    onDestroy(() => {
        $selectedLabours = {
            selectedLabourId: selectedLabourId,
            labourPos: labourPos,
            phasePos: phasePos
        };
    });

    const updateSelectedLabour = (labourId:string) => {
        if (selectedLabourId === labourId) selectedLabourId = ''
        else selectedLabourId = labourId
        selectedLabourId = selectedLabourId
    }

    const selectedLabourStyle = (
        labourId:string,
        selectedLabourId:string
    ): string => {
        if (selectedLabourId !== labourId) return "";
        return "bg-indigo-600 text-white";
    };

    const determineEvenBgColor = (
        labourId:string,
        selectedLabourId:string
    ): string => {
        if (selectedLabourId !== labourId) return "even:bg-black";
        return "even:bg-indigo-600";
    };
</script>

<div class="overflow-x-auto text-primary-content mx-auto">
    <!-- <table class="table table-zebra"> -->
    <table class="table">
        <!-- head -->
        <thead class="text-primary-content">
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Unit</th>
                <th>Rate Amount</th>
                <th>Currency</th>
                <th>Contractor Name</th>
                <th>Contractor Contact</th>
                <th>updatedTime</th>
            </tr>
        </thead>
        <tbody>
            {#if data !== undefined}
                {#each data.content as material}
                    <tr
                        class={`hover:bg-indigo-400 hover:text-black even:text-white hover:even:text-black hover:even:bg-indigo-400 cursor-pointer ease-in transition-all duration-200
                        ${selectedLabourStyle(
                            material.id,
                            selectedLabourId
                        )}
                        ${determineEvenBgColor(
                            material.id,
                            selectedLabourId
                        )}`}
                        on:click={() => updateSelectedLabour(material.id)}
                    >
                        <td>{material.id}</td>
                        <td>{material.name}</td>
                        <td>{material.unit}</td>
                        <td>{material.rateAmount.value}</td>
                        <td>{material.rateAmount.currencyCode}</td>
                        <td>{material.contractorName}</td>
                        <td>{material.contractorContact}</td>
                        <td>{material.updatedTime}</td>
                    </tr>
                {/each}
            {/if}
        </tbody>
    </table>
</div>