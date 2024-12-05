<script lang="ts">
  export let name
  export let placeholder = ''
  export let value
  export let classes = ''
</script>

<input
  {name}
  type="text"
  bind:value
  {placeholder}
  class={'input input-bordered input-primary w-full max-w-xs' + classes}
/>
