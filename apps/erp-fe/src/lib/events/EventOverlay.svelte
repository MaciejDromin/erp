<script lang="ts">
  export let eventData
  export let triggerClose

  const notifyClose = () => {
    triggerClose = true
  }
</script>

<div role="alert" class="alert max-w-[40rem] top-3/4 fixed z-[999] right-10">
  <svg
    xmlns="http://www.w3.org/2000/svg"
    fill="none"
    viewBox="0 0 24 24"
    class="stroke-info h-6 w-6 shrink-0"
  >
    <path
      stroke-linecap="round"
      stroke-linejoin="round"
      stroke-width="2"
      d="M13 16h-1v-4h-1m1-4h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z"
    ></path>
  </svg>
  <span>Your file is ready to download!</span>
  <a href={eventData.url}>{eventData.name}</a>
  <div>
    <button class="btn btn-sm btn-primary" on:click={notifyClose}>Close</button>
  </div>
</div>
