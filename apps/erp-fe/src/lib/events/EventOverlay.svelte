<script lang="ts">
  import { apiRequest } from '$lib/scripts/uiHttpRequests.ts'
  import { HttpMethods } from '$lib/types/httpMethods'

  export let eventData
  export let triggerClose

  const notifyClose = () => {
    triggerClose = true
  }

  let filename = ''

  const downloadFile = async (fileLocation) => {
    await apiRequest(
      '/artifact-manager/?filePath=' + fileLocation,
      HttpMethods.GET
    )
      .then((res) => {
        console.log(res)
        const disposition = res.headers.get('Content-Disposition')
        filename = disposition.split(/;(.+)/)[1].split(/=(.+)/)[1]
        if (filename.toLowerCase().startsWith("utf-8''"))
          filename = decodeURIComponent(filename.replace(/utf-8''/i, ''))
        else filename = filename.replace(/['"]/g, '')
        return res.blob()
      })
      .then((blob) => {
        let url = window.URL.createObjectURL(blob)
        let a = document.createElement('a')
        a.href = url
        a.download = filename
        document.body.appendChild(a) // append the element to the dom
        a.click()
        a.remove()
      })
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
  <button on:click={downloadFile(eventData.url)}>{eventData.name}</button>
  <div>
    <button class="btn btn-sm btn-primary" on:click={notifyClose}>Close</button>
  </div>
</div>
