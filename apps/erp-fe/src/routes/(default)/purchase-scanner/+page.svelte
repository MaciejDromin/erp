<script>
  import { enhance } from '$app/forms'
  import { apiRequestFile } from '$lib/scripts/uiHttpRequests.ts'
  import { HttpMethods } from '$lib/types/httpMethods.ts'

  const sendFile = () => {
    let fileInput = document.getElementById('fileInput')
    if (fileInput.files[0]) {
      for (const file of fileInput.files) {
        let reader = new FileReader()
        reader.onload = function () {
          uploadFile(reader.result, file.name)
        }
        reader.readAsArrayBuffer(file)
      }
    }
  }

  function uploadFile(contents, filename) {
    filename = encodeURI(filename)
    apiRequestFile('/purchase-scanner', HttpMethods.POST, filename, contents)
  }
</script>

<div id="currency" class="flex flex-col gap-3 px-10 pt-10">
  <h1 class="p-12 text-3xl text-black">Purchase Scanner</h1>
  <div class="mx-auto flex flex-col gap-3 py-6">
    <input
      type="file"
      name="file"
      id="fileInput"
      required
      class="file-input file-input-bordered w-full max-w-xs"
    />
    <button class="btn btn-primary mx-auto" on:click={sendFile}
      >Scan Purchases</button
    >
  </div>
</div>
