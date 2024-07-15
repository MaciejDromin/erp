<script>
  import { Line, Bar, Pie, Doughnut } from 'svelte-chartjs'
  import Text from '$lib/widgets/Text.svelte'
  import 'chart.js/auto'

  export let chartData
  export let chartType

  const chartTypeMap = {
    PIE: Pie,
    BAR: Bar,
    LINE: Line,
    DOUGHNUT: Doughnut,
    TEXT: Text,
  }
</script>

<div class="w-fit mx-auto p-8">
  <svelte:component this={chartTypeMap[chartType]} {...{ data: chartData }} />
</div>
