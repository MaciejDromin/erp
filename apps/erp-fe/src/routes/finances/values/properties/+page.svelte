<script lang="ts">
  import Pageable from '$lib/Pageable.svelte'
  import ObjectValueTable from '$lib/finances/object-value/ObjectValueTable.svelte'
  import type { PageData } from './$types'
  import { ObjectType } from '$lib/finances/types/financialTypes'
  import FeatureMenuBar from '$lib/FeatureMenuBar.svelte'

  export let data: PageData

  let config = {
    title: 'Item Values',
    addButton: {
      url: '/finances/values/properties/add',
    },
    editButton: {
      disabled: false,
    },
    deleteButton: {
      disabled: false,
    },
  }
</script>

<FeatureMenuBar {config} />
<div id="object-value" class="flex flex-col gap-3 px-10 pt-10">
  <div class="stats shadow mx-auto">
    <div class="stat">
      <div class="stat-figure text-primary text-4xl">$</div>
      <div class="stat-title">Total Amount</div>
      <div class="stat-value text-primary">
        {data.objectsValue.totalAmount.value}
        {data.objectsValue.totalAmount.currencyCode}
      </div>
      <div class="stat-desc">Total amount of all objects</div>
    </div>

    <div class="stat">
      <div class="stat-figure text-secondary text-3xl">0..*</div>
      <div class="stat-title">Objects Count</div>
      <div class="stat-value text-secondary">
        {data.objectsValue.objectsCount}
      </div>
      <div class="stat-desc">How much objects were counted</div>
    </div>
  </div>

  <Pageable
    endpoint="/finances/object-value"
    additionalSearch={`&objectType=${ObjectType.PROPERTY}`}
    additionalParams={{ objectType: ObjectType.PROPERTY }}
    component={ObjectValueTable}
  />
</div>
