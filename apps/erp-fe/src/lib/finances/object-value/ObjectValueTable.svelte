<script lang="ts">
  import { HttpMethods } from '$lib/types/httpMethods'
  import { ObjectType } from '$lib/finances/types/financialTypes'

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
          <tr>
            <td>{objectValue.uuid}</td>
            <td>{getName(objectNameMap, objectValue.objectId)}</td>
            <td>{objectValue.amount.value}</td>
            <td>{objectValue.amount.currencyCode}</td>
          </tr>
        {/each}
      {/if}
    </tbody>
  </table>
</div>
