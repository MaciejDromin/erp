<script lang="ts">
  import { onMount } from 'svelte'

  export let modalId: string
  export let buttonName: string = ''
  let dialog: HTMLDialogElement
  let showModal: boolean = false
  onMount(() => {
    dialog = document.getElementById(modalId)! as HTMLDialogElement
    dialog.addEventListener('close', () => {
      showModal = false
    })
  })
</script>

<div>
  <!-- Open the modal using ID.showModal() method -->
  <div
    on:click|preventDefault={() => {
      showModal = true
      dialog.showModal()
    }}
  >
    <slot name="button">
      <button class="btn">{buttonName}</button>
    </slot>
  </div>
  <dialog id={modalId} class="modal">
    <form method="dialog" class="modal-box bg-white px-12 py-6">
      {#if showModal}
        <slot />
        <div class="modal-action">
          <!-- if there is a button in form, it will close the modal -->
          <button class="btn">Close</button>
        </div>
      {/if}
    </form>
  </dialog>
</div>
