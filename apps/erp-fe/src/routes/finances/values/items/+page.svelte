<script lang="ts">
  import { ObjectType } from '$lib/finances/types/financialTypes'
  import type { PageData } from './$types'
  import ObjectValueTable from '$lib/finances/object-value/ObjectValueTable.svelte'
  import Pageable from '$lib/Pageable.svelte'
  import FeatureMenuBar from '$lib/FeatureMenuBar.svelte'
  import { Services } from '$lib/types/services.ts'
  import { genericStore } from '$lib/stores/genericStore.ts'

  export let data: PageData

  let config = {
    title: 'Item Values',
    addButton: {
      url: '/finances/values/items/add',
    },
    editButton: {
      url: '/finances/values/edit',
      disabled: true,
    },
    deleteButton: {
      disabled: false,
    },
    delete: {
      endpoint: '/finances/object-value',
      keys: ['finances', 'objectvalues'],
    },
    service: Services.FINANCES,
  }

  const updateConfig = () => {
    if ($genericStore.finances === undefined) return

    if ($genericStore.finances.objectvalues === undefined) {
      config.editButton.disabled = true
      return
    }

    if ($genericStore.finances.objectvalues.length === 1) {
      config.editButton.url = `/finances/values/items/${$genericStore.finances.objectvalues[0].uuid}/edit`
      config.editButton.disabled = false
      config = config
      return
    }

    config.editButton.disabled = true
  }

  $: $genericStore, updateConfig()
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
    additionalSearch={`&objectType=${ObjectType.ITEM}`}
    additionalParams={{ objectType: ObjectType.ITEM }}
    component={ObjectValueTable}
  />
</div>
