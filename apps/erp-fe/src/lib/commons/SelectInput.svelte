<script lang="ts">
  export let name
  export let value
  export let options
  export let classes

  const provideValue = (opt) => {
    if (typeof opt === 'object') {
      return JSON.stringify(opt)
    }
    return opt
  }
</script>

<select
  {name}
  class={'select select-primary w-full max-w-xs' + classes}
  bind:value={value}
>
  {#each options as opt}
    <option value={provideValue(opt)}>{opt}</option>
  {/each}
</select>
