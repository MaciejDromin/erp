<script lang="ts">
  import { onMount } from 'svelte'
  import { selectedMaterials } from '$lib/planner/stores/selectedMaterials'
  import { selectedLabours } from '$lib/planner/stores/selectedLabours'
  import Modal from '$lib/Modal.svelte'
  import Pageable from '$lib/Pageable.svelte'
  import MaterialsTable from '$lib/planner/materials/MaterialsTable.svelte'
  import LaboursTable from '$lib/planner/labours/LaboursTable.svelte'

  let name = ''
  let currency = ''
  let phases: {
    name: string
    phaseTime: number | undefined
    currency: string
  }[] = []
  let materialsPerPhase: [
    { materialId: string; quantity: number | undefined },
  ][] = []
  let labourPerPhase: [{ labourId: string; quantity: number | undefined }][] =
    []

  onMount(() => {
    $selectedMaterials = {}
    $selectedLabours = {}
  })

  const addPhase = () => {
    phases = phases.concat({
      name: '',
      phaseTime: undefined,
      currency: '',
    })
    materialsPerPhase.push([
      {
        materialId: '',
        quantity: undefined,
      },
    ])
    // materialsPerPhase = materialsPerPhase
    labourPerPhase.push([
      {
        labourId: '',
        quantity: undefined,
      },
    ])
    // labourPerPhase = labourPerPhase
  }
  const addMaterial = (i: number) => {
    materialsPerPhase[i].push({
      materialId: '',
      quantity: undefined,
    })
    materialsPerPhase = materialsPerPhase
  }
  const addLabour = (i: number) => {
    labourPerPhase[i].push({
      labourId: '',
      quantity: undefined,
    })
    labourPerPhase = labourPerPhase
  }
  const createPhases = (phases: any, materials: any, labours: any): any => {
    const numOfPhases = phases.length
    let ret: any[] = []
    for (let i = 0; i < numOfPhases; i++) {
      ret.push({
        name: phases[i].name,
        phaseTime: phases[i].phaseTime,
        currency: phases[i].currency,
        materials: materials[i],
        labours: labours[i],
        subPhases: [],
      })
    }
    return ret
  }

  selectedMaterials.subscribe((mat) => {
    if (mat.materialPos === undefined || mat.phasePos === undefined) return
    materialsPerPhase[mat.phasePos][mat.materialPos].materialId =
      mat.selectedMaterialId
    materialsPerPhase = materialsPerPhase
  })

  const determineMaterialButtonName = (materialId: string): string => {
    if (materialId === '') return 'Select Material'
    return 'Material Selected'
  }

  selectedLabours.subscribe((lab) => {
    if (lab.labourPos === undefined || lab.phasePos === undefined) return
    labourPerPhase[lab.phasePos][lab.labourPos].labourId = lab.selectedLabourId
    labourPerPhase = labourPerPhase
  })

  const determineLabourButtonName = (labourId: string): string => {
    if (labourId === '') return 'Select Labour'
    return 'Labour Selected'
  }
</script>

<div id="investment-add" class="flex flex-col gap-3 px-10 pt-10">
  <form method="POST" class="mx-auto flex flex-col gap-3 py-6 min-w-[40rem]">
    <div class="flex flex-row gap-3 mx-auto">
      <input
        type="text"
        placeholder="Investment Name"
        class="input input-bordered input-primary w-full max-w-xs"
        bind:value={name}
      />
      <input
        type="text"
        placeholder="Investment Currency"
        class="input input-bordered input-primary w-full max-w-xs"
        bind:value={currency}
      />
      <input
        name="creation"
        class="hidden"
        value={JSON.stringify({
          name: name,
          currency: currency,
          investmentPhases: createPhases(
            phases,
            materialsPerPhase,
            labourPerPhase
          ),
        })}
      />
    </div>
    <div class="flex flex-col gap-3">
      {#each phases as phase, i}
        <div class="flex flex-col w-full">
          <h1 class="text-xl mx-auto text-black py-3">
            Phase {i + 1}
          </h1>
          <div class="h-[2px] w-full bg-black rounded-full my-3"></div>
          <div class="flex flex-row gap-3">
            <input
              type="text"
              placeholder="Phase Name"
              class="input input-bordered input-primary w-full max-w-xs"
              bind:value={phase.name}
            />
            <input
              type="text"
              placeholder="Phase Time"
              class="input input-bordered input-primary w-full max-w-xs"
              bind:value={phase.phaseTime}
            />
            <input
              type="text"
              placeholder="Currency"
              class="input input-bordered input-primary w-full max-w-xs"
              bind:value={phase.currency}
            />
          </div>
          {#each materialsPerPhase[i] as material, mi}
            <h1 class="text-xl mx-auto text-black py-3">
              Material {mi + 1}
            </h1>
            <div class="h-[2px] w-full bg-black rounded-full my-3"></div>
            <div class="flex flex-row gap-3 mx-auto">
              <div class="mr-auto">
                <Modal
                  modalId={`material-modal-ph-${i}-m-${mi}`}
                  buttonName={determineMaterialButtonName(
                    materialsPerPhase[i][mi].materialId
                  )}
                >
                  <Pageable
                    endpoint="/planner/materials"
                    component={MaterialsTable}
                    additionalParams={{
                      phasePos: i,
                      materialPos: mi,
                    }}
                  />
                </Modal>
              </div>
              <input
                type="text"
                placeholder="Material Quantity"
                class="input input-bordered input-primary w-full max-w-xs"
                bind:value={materialsPerPhase[i][mi].quantity}
              />
            </div>
          {/each}
          <button
            on:click|preventDefault={() => addMaterial(i)}
            class="btn btn-primary mx-auto my-3">Add Material</button
          >
          {#each labourPerPhase[i] as labour, li}
            <h1 class="text-xl mx-auto text-black py-3">
              Labour {li + 1}
            </h1>
            <div class="h-[2px] w-full bg-black rounded-full my-3"></div>
            <div class="flex flex-row gap-3 mx-auto">
              <div class="mr-auto">
                <Modal
                  modalId={`labour-modal-ph-${i}-m-${li}`}
                  buttonName={determineLabourButtonName(
                    labourPerPhase[i][li].labourId
                  )}
                >
                  <Pageable
                    endpoint="/planner/labours"
                    component={LaboursTable}
                    additionalParams={{
                      phasePos: i,
                      labourPos: li,
                    }}
                  />
                </Modal>
              </div>
              <input
                type="text"
                placeholder="Material Quantity"
                class="input input-bordered input-primary w-full max-w-xs"
                bind:value={labourPerPhase[i][li].quantity}
              />
            </div>
          {/each}
          <button
            on:click|preventDefault={() => addLabour(i)}
            class="btn btn-primary mx-auto my-3">Add Labour</button
          >
        </div>
      {/each}
    </div>
    <button on:click|preventDefault={addPhase} class="btn btn-primary mx-auto"
      >Add Phase</button
    >
    <button class="btn btn-primary">Create Investment</button>
  </form>
</div>
