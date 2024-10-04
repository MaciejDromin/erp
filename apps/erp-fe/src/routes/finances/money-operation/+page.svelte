<script lang="ts">
  import Pageable from '$lib/Pageable.svelte'
  import MoneyOpearationTable from '$lib/finances/money-operation/MoneyOperationTable.svelte'
  import FeatureMenuBar from '$lib/FeatureMenuBar.svelte'

  let config = {
    title: 'Money Operations',
    addButton: {
      url: '/finances/money-operation/add',
    },
    editButton: {
      disabled: true,
    },
    deleteButton: {
      disabled: true,
    },
  }
</script>

<FeatureMenuBar {config} />
<div id="money-operation" class="flex flex-col gap-3 px-10 pt-10">
  <Pageable
    endpoint="/finances/money-operation"
    component={MoneyOpearationTable}
  />
</div>
