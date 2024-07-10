<script>
  import { Line, Bar, Pie, Doughnut } from 'svelte-chartjs'

  export let chartData;
  export let chartType;
  
  const chartTypeMap = {
    "PIE": Pie,
    "BAR": Bar,
    "LINE": Line,
    "DOUGHNUT": Doughnut
  }

</script>
<div>
  {chartType}
  <svelte:component this={chartTypeMap[chartType]} {...{data: chartData}} />
</div>
