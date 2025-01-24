<script lang="ts">
  export let name
  export let placeholder = ''
  export let value
  export let classes = ''
  export let borderColor = 'input-primary'
  export let error = undefined

  borderColor =
    error === undefined
      ? borderColor
      : 'input-error-red focus:border-error-red focus:outline-error-red'
</script>

<label class="form-control w-full max-w-xs">
  <input
    {name}
    type="password"
    bind:value
    {placeholder}
    class={`input input-bordered ${borderColor} ${classes}`}
  />
  {#if error !== undefined}
    <div class="label">
      <span class="label-text-alt text-rose-700">{error}</span>
    </div>
  {/if}
</label>
