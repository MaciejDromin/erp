<script>

  export let data;

</script>

<div class="stats shadow">
  <div class="stat">
    <div class="stat-title">{data.labels[0]}</div>
    <div class="stat-value">{data.datasets[0].label}</div>
  </div>
</div>
