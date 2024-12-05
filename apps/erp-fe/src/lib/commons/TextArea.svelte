<script lang="ts">
  export let name
  export let value
  export let placeholder = ''
  export let classes
</script>

<textarea
  {name}
  bind:value
  {placeholder}
  class={'textarea textarea-primary' + classes}
/>
