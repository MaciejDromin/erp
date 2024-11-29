<script lang="ts">
  import { HttpMethods } from '$lib/types/httpMethods'
  import { ObjectType } from '$lib/finances/types/financialTypes'
  import { onDestroy, onMount } from 'svelte'
  import { genericStore } from '$lib/stores/genericStore.ts'

  export let data: any = undefined
  export let objectType: ObjectType = undefined

  let objectNameMap: { [id: string]: string } = {}

  const getObjectNames = async (dt: any) => {
    if (dt === undefined) return
    const res = await fetch(
      `/api/inventory/${determinePath(objectType)}/object-names`,
      {
        method: HttpMethods.POST,
        body: JSON.stringify({
          itemIds: extractObjectIds(dt.content),
        }),
      }
    )
    objectNameMap = await res.json()
  }

  const determinePath = (objectType: ObjectType) => {
    switch (objectType) {
      case ObjectType.ITEM:
        return 'items'
        break
      case ObjectType.PROPERTY:
        return 'properties'
        break
      case ObjectType.VEHICLE:
        return 'vehicles'
        break
    }
    return 'none'
  }

  const extractObjectIds = (items: any[]): string[] => {
    let ret: string[] = []
    items.forEach((item) => ret.push(item.objectId))
    return ret
  }

  $: data && getObjectNames(data)

  const getName = (nameMap: { [id: string]: string }, objectId: string) => {
    return nameMap[objectId]
  }

  let selectedObjectValues: Map<string, string> = new Map()

  onMount(() => {
    if (
      $genericStore.finances !== undefined &&
      $genericStore.finances.objectvalues !== undefined
    ) {
      $genericStore.finances.objectvalues.forEach((catg) =>
        selectedObjectValues.set(catg.id, catg)
      )
    }
  })

  onDestroy(() => {
    $genericStore.finances = {}
    $genericStore.finances.objectvalues = Array.from(
      selectedObjectValues.values()
    )
  })

  const updateObjectValuesList = (objectValue: string) => {
    if (selectedObjectValues.has(objectValue.id)) {
      selectedObjectValues.delete(objectValue.id)
    } else {
      selectedObjectValues.set(objectValue.id, objectValue)
    }
    selectedObjectValues = selectedObjectValues
    $genericStore.finances = {}
    $genericStore.finances.objectvalues = Array.from(
      selectedObjectValues.values()
    )
  }

  const objectValueSelectedStyles = (
    objectValueMap: Map<string, string>,
    objectValueId: string
  ): string => {
    if (!objectValueMap.has(objectValueId)) return ''
    return 'bg-indigo-600 text-white'
  }

  const determineEvenBgColor = (
    objectValueMap: Map<string, string>,
    objectValueId: string
  ): string => {
    if (!objectValueMap.has(objectValueId)) return 'even:bg-black'
    return 'even:bg-indigo-600'
  }
</script>

<div class="overflow-x-auto text-primary-content mx-auto">
  <!-- <table class="table table-zebra">  -->
  <table class="table">
    <!-- head -->
    <thead class="text-primary-content">
      <tr>
        <th>ID</th>
        <th>Object Name</th>
        <th>Amount</th>
        <th>Currency</th>
      </tr>
    </thead>
    <tbody>
      {#if data !== undefined}
        {#each data.content as objectValue}
          <tr
            class={`hover:bg-indigo-400 hover:text-black even:text-white hover:even:text-black hover:even:bg-indigo-400 cursor-pointer ease-in transition-all duration-200
        ${objectValueSelectedStyles(selectedObjectValues, objectValue.id)}
        ${determineEvenBgColor(selectedObjectValues, objectValue.id)}`}
            on:click={() => updateObjectValuesList(objectValue)}
          >
            <td>{objectValue.id}</td>
            <td>{getName(objectNameMap, objectValue.objectId)}</td>
            <td>{objectValue.amount.value}</td>
            <td>{objectValue.amount.currencyCode}</td>
          </tr>
        {/each}
      {/if}
    </tbody>
  </table>
</div>
