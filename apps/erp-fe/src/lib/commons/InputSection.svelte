<script lang="ts">
  export let name
  export let classes = ''
</script>

<fieldset class={'border-2 border-stone-600 p-2 rounded flex' + classes}>
  <legend class="text-black">{name}</legend>
  <slot />
</fieldset>
